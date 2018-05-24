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
              Auth.message !== "" ? m(".notification.is-warning", Auth.message) : null,
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
                        m("input.input[type='text']", {
                          value: Auth.username,
                          class: Auth.validateUsername && !Auth.isUsernameValid() ? "is-danger" : "",
                          oninput: m.withAttr("value", Auth.setUsername),
                          onfocusout: function () { Auth.setValidateUsername(true) }
                        })
                      )
                    ]
                  ),
                  m(".field",
                    [
                      m("label.label",
                        "Password"
                      ),
                      m(".control",
                        m("input.input[type='password']", {
                          value: Auth.password,
                          class: Auth.validatePassword && !Auth.isPasswordValid() ? "is-danger" : "",
                          oninput: m.withAttr("value", Auth.setPassword),
                          onfocusout: function () { Auth.setValidatePassword(true) },
                        })
                      )
                    ]
                  ),
                  m(".field",
                    m(".control",
                      m("button.button.is-primary[type='submit']",
                        {
                          disabled: !Auth.canSubmit()
                        }, "Login")
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
