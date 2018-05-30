const m = require("mithril");
const Dummy = require("../models/Dummy");
const Auth = require("../models/Auth");

let state = {
  message: "",

  reset: function () {
    this.message = ""
  }
};

module.exports = {
  onremove: function () {
    state.reset();
  },

  view: function () {
    return m("section.section",
      m(".container",
        m(".columns.is-centered",
          m(".column.is-one-third",
            [
              m("h1.title.is-1",
                "Project"
              ),
              state.message !== "" ? m("pre.notification.is-warning", state.message) : null,
            ]
          )
        ),
        m(".columns.is-centered",
          m(".column.is-one-third", [
            m("button.button", {
              onclick: function () {
                Dummy.dummy().then(
                  function (result) {
                    state.message = result.data.greeting;
                  }
                )
              }
            }, "Greet"),
          ])
        ),
        m(".columns.is-centered",
          m(".column.is-one-third", [
            m("button.button", {onclick: Auth.logout}, "Logout")
          ])
        )
      )
    )
  }
};