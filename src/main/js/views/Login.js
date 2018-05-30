const m = require("mithril");
const Auth = require("../models/Auth");

let state = {
  username: "",
  validateUsername: false,
  password: "",
  validatePassword: false,
  message: "",

  reset: function () {
    state.username = "";
    state.validateUsername = false;
    state.password = "";
    state.validatePassword = false;
    state.message = "";
  },

  setUsername: function (value) {
    state.username = value
  },

  setValidateUsername: function (validate) {
    state.validateUsername = validate
  },

  isUsernameValid: function () {
    return state.username !== ""
  },

  setPassword: function (value) {
    state.password = value
  },

  setValidatePassword: function (validate) {
    state.validatePassword = validate
  },

  isPasswordValid: function () {
    return state.password !== ""
  },

  canSubmit: function () {
    return state.isUsernameValid() && state.isPasswordValid()
  },
};

module.exports = {
  onremove: function() {
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
              state.message !== "" ? m(".notification.is-warning", state.message) : null,
              m("form", {
                  onsubmit: function (event) {
                    event.preventDefault();
                    Auth.login(state.username, state.password).then(function () {
                      m.route.set("/home");
                    }).catch(function (error) {
                      state.password = "";
                      state.message = error.message;
                    })
                  }
                }, [
                  m(".field",
                    [
                      m("label.label",
                        "Username"
                      ),
                      m(".control",
                        m("input.input[type='text']", {
                          value: state.username,
                          class: state.validateUsername && !state.isUsernameValid() ? "is-danger" : "",
                          oninput: m.withAttr("value", state.setUsername),
                          onfocusout: function () {
                            state.setValidateUsername(true)
                          }
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
                          value: state.password,
                          class: state.validatePassword && !state.isPasswordValid() ? "is-danger" : "",
                          oninput: m.withAttr("value", state.setPassword),
                          onfocusout: function () {
                            state.setValidatePassword(true)
                          },
                        })
                      )
                    ]
                  ),
                  m(".field",
                    m(".control",
                      m("button.button.is-primary[type='submit']",
                        {
                          disabled: !state.canSubmit()
                        }, "Login")
                    )
                  ),
                  m(".field",
                    m(".control",
                      m("button.button[type='button']",
                        {
                          onclick: function () {
                            m.route.set("/register")
                          }
                        }, "Register"
                      )
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
