const m = require("mithril");
const Dummy = require("../models/Dummy");

module.exports = {
  view: function () {
    return m("section.section",
      m(".container",
        m("button", {onclick: Dummy.dummy}, "Greet")
      )
    )
  }
};