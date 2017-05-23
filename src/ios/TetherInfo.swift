import Foundation
import SystemConfiguration.CaptiveNetwork

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

    var currentSSID = "false"
    let interfaces:CFArray! = CNCopySupportedInterfaces()
    if interfaces != nil {
      for i in 0..<CFArrayGetCount(interfaces){
        let interfaceName = CFArrayGetValueAtIndex(interfaces, i)
        let rec = unsafeBitCast(interfaceName, to: AnyObject.self)
        let unsafeInterfaceData = CNCopyCurrentNetworkInfo("\(rec)" as CFString)
        if unsafeInterfaceData != nil {
          let interfaceData = unsafeInterfaceData! as Dictionary!
          currentSSID = interfaceData["SSID"] as! String
        }
      }
    }

    // dont do anything
    pluginResult = CDVPluginResult(
      status: CDVCommandStatus_OK,
      messageAs: currentSSID
    )

    self.commandDelegate.send(
      pluginResult,
      callbackId: command.callbackId
    )
  }
}
