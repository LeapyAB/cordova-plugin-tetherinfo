
import SystemConfiguration

@objc(TetherInfo) class TetherInfo : CDVPlugin {


  @objc(listip:)
  func listip(command: CDVInvokedUrlCommand) {
    var pluginResult = CDVPluginResult(
      status: CDVCommandStatus_ERROR
    )

    let msg = "hello"

    if msg.characters.count > 0 {
      pluginResult = CDVPluginResult(
        status: CDVCommandStatus_OK,
        messageAs: msg
      )
    }

    self.commandDelegate.send(
      pluginResult,
      callbackId: command.callbackId
    )
  }

  @objc(checkusb:)
  func checkusb(command: CDVInvokedUrlCommand) {
    var pluginResult = CDVPluginResult(
      status: CDVCommandStatus_ERROR
    )

    //let sc = SCDynamicStoreCreate(nil, "com.apple.wirelessmodemsettings.MISManager" as CFString, nil, nil);
    //let info = SCDynamicStoreCopyValue(sc, "com.apple.MobileInternetSharing" as CFString);

    /*var currentSSID = ""
    let interfaces:CFArray! = CNCopySupportedInterfaces()
    if interfaces != nil {
      for i in 0..<CFArrayGetCount(interfaces) {
        let interfaceName: UnsafeRawPointer = CFArrayGetValueAtIndex(interfaces, i)
        let rec = unsafeBitCast(interfaceName, to: AnyObject.self)
        let unsafeInterfaceData = CNCopyCurrentNetworkInfo("\(rec)" as CFString)
        currentSSID = currentSSID + (rec as! String)

        if unsafeInterfaceData != nil {
          let interfaceData = unsafeInterfaceData! as NSDictionary
          //currentSSID = interfaceData["SSID"] as! String
        }
      }
    }*/



    pluginResult = CDVPluginResult(
      status: CDVCommandStatus_OK,
      messageAs: "false"
    )

    self.commandDelegate.send(
      pluginResult,
      callbackId: command.callbackId
    )
  }
}
