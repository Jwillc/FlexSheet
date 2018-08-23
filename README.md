# FlexSheet
Clipboard change listener. Copy an address and immediately get the long/lat. Save this information to a spreadsheet in CSV format on the device.
# Usage
For use within the Amazon Flex app. Copy addresses within the app. Save to a spreadsheet and import the sheet into an external routing service such as Circuit.
# The Circuit App Format:
> Circuit takes your delivery route, or list of addresses to visit, and automatically reorders the stops to prevent backtracking and wasted miles, letting you save multiple hours everyday as your deliver up to 30% faster.

![Circuit Spreadsheet format](https://i.imgur.com/yj8BpMW.png)
# Project Goal:
We want to grab all the addresses from the list within the Amazon Flex app. All at once or one at a time, without switching apps. Convert the addresses to the required format. Write the data to a CSV file so that we can import the sheet into the Circuit app for routing.
