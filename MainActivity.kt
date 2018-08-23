package us.flexswag.flexsheet

import android.content.ClipboardManager
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.toast
import java.io.FileWriter
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {

    private var mClipboardManager: ClipboardManager? = null
    internal var bHasClipChangedListener = false
    private val CSV_HEADER = "address,long,lat"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (mClipboardManager != null) {
            RegPrimaryClipChanged()
        }
    }

    private val mOnPrimaryClipChangedListener = ClipboardManager.OnPrimaryClipChangedListener {
        try {
            val clip = mClipboardManager!!.primaryClip
            val newClip = clip.getItemAt(0).text.toString()
            val coder = Geocoder(this)
            var longitude = String()
            var latitude = String()

            try {
                val addresses = coder.getFromLocationName(newClip, 50) as ArrayList<Address>
                for (add in addresses) {
                    longitude = add.longitude.toString()
                    latitude = add.latitude.toString()
                    runOnUiThread {
                        toast("$longitude $latitude")
                    }
                }
                doAsyncResult {
                    writeCsvFile(newClip, longitude, latitude)
                }
            } catch (e: IOException) {
                Toast.makeText(this@MainActivity, "Unable to get coordinates.", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
            UnRegPrimaryClipChanged()
            RegPrimaryClipChanged()
        } catch (e: Exception) {
            runOnUiThread {
                Toast.makeText(applicationContext,
                        "Clip change error: " + e.message,
                        Toast.LENGTH_LONG)
                        .show()
            }
        }
    }

    private fun writeCsvFile(address: String, long: String, lat: String){
        var fileWriter: FileWriter? = null
        try {
            fileWriter = FileWriter(Environment.getExternalStorageDirectory().path + "/sheetTest_Addresses.csv")

            fileWriter.append(CSV_HEADER)
            fileWriter.append('\n')

            val customers = Arrays.asList(
                    Customer(address, long, lat))

            for (customer in customers) {
                fileWriter.append(customer.address)
                fileWriter.append(',')
                fileWriter.append(customer.long)
                fileWriter.append(',')
                fileWriter.append(customer.lat)
                fileWriter.append('\n')
            }
        } catch (e: Exception) {
            toast("Writing CSV error!")
            e.printStackTrace()
        } finally {
            try {
                fileWriter!!.flush()
                fileWriter.close()
            } catch (e: IOException) {
                toast("Flushing/closing error!")
                e.printStackTrace()
            }
        }

    }

    private fun RegPrimaryClipChanged() {
        if (!bHasClipChangedListener) {
            mClipboardManager!!.addPrimaryClipChangedListener(mOnPrimaryClipChangedListener)
            bHasClipChangedListener = true
        }
    }

    private fun UnRegPrimaryClipChanged() {
        if (bHasClipChangedListener) {
            mClipboardManager!!.removePrimaryClipChangedListener(mOnPrimaryClipChangedListener)
            bHasClipChangedListener = false
        }
    }
}
