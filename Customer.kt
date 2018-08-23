package us.flexswag.flexsheet

class Customer {
    var address: String? = null
    var long: String? = null
    var lat: String? = null

    constructor() {}
    constructor(address: String?, long: String?, lat: String?) {
        this.address = address
        this.long = long
        this.lat = lat
    }

    override fun toString(): String {
        return "Customer [address=$address, long=$long, lat=$lat]"
    }
}