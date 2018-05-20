const m = require("mithril");
const Dummy = require("../models/Dummy");
const Auth = require("../models/Auth");

module.exports = {
    view: function () {
        return m("section.section",
            m(".container",
                m("button", {onclick: Dummy.dummy}, "Greet"),
                m("button", {onclick: Auth.logout}, "Logout")
            )
        )
    }
};