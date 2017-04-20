@objc(TetherInfo) class TetherInfo : CDVPlugin {


  @objc(listip) func listip(command: CDVInvokedUrlCommand) {
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

    self.commandDelegate.sendPluginResult(
      pluginResult,
      callbackId: command.callbackId
    )
  }

  @objc(checkusb) func checkusb(command: CDVInvokedUrlCommand) {
    var pluginResult = CDVPluginResult(
      status: CDVCommandStatus_ERROR
    )

    let msg = "false"

    pluginResult = CDVPluginResult(
      status: CDVCommandStatus_OK,
      messageAs: msg
    )

    self.commandDelegate.sendPluginResult(
      pluginResult,
      callbackId: command.callbackId
    )
  }
}
