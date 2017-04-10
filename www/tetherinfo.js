/*global cordova, module*/


'use strict';
var exec = require('cordova/exec');

var fail = function(o) {
    console.error("Error " + JSON.stringify(o));
};

module.exports = {
  checkusb : function(success) {
      return exec(success, fail, "TetherInfo", "checkusb", [ ]);
  },
  listip : function(success) {
      return exec(success, fail, "TetherInfo", "listip", [ ]);
  }
};
