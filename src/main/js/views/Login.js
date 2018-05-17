const m = require("mithril");
const Auth = require("../models/Auth");

module.exports = {
  view: function () {
    return m("section.section",
      m(".container",
        m(".columns.is-centered",
          m(".column.is-one-third",
            [
              m("h1.title.is-1",
                "Project"
              ),
              m("form", {
                onsubmit: function (e) {
                  e.preventDefault();
                  Auth.login();
                }
              }, [
                  m(".field",
                    [
                      m("label.label",
                        "Username"
                      ),
                      m(".control",
                        m("input.input[type='text']", {oninput: m.withAttr("value", Auth.setUsername), value: Auth.username})
                      )
                    ]
                  ),
                  m(".field",
                    [
                      m("label.label",
                        "Password"
                      ),
                      m(".control",
                        m("input.input[type='password']", {oninput: m.withAttr("value", Auth.setPassword), value: Auth.password})
                      )
                    ]
                  ),
                  m(".field",
                    m(".control",
                      m("button.button.is-primary[type='submit']", "Login")
                    )
                  )
                ]
              )
            ]
          )
        )
      )
    )
  }
};
