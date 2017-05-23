import SystemConfiguration
import MobileDevice

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

    // dont do anything
    pluginResult = CDVPluginResult(
      status: CDVCommandStatus_OK,
      messageAs: "true"
    )

    self.commandDelegate.send(
      pluginResult,
      callbackId: command.callbackId
    )
  }
}
