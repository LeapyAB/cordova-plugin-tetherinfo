/*global cordova, module*/

module.exports = {
  listip : function(success) {
      return exec(success, fail, "TetherInfo", "listip", [ ]);
  }
};
