const m = require("mithril");
const Auth = require("../models/Auth");

let state = {
  username: "",
  validateUsername: false,
  password: "",
  validatePassword: false,
  confirmPassword: "",
  validateConfirmPassword: false,
  message: "",

  reset: function () {
    state.username = "";
    state.validateUsername = false;
    state.password = "";
    state.validatePassword = false;
    state.confirmPassword = "";
    state.validateConfirmPassword = "";
    state.message = "";
  },

  isUsernameValid: function () {
    return state.username.length > 4;
  },

  isPasswordValid: function () {
    return state.password.length > 4;
  },

  isConfirmPasswordValid: function () {
    return state.isPasswordValid() && state.password === state.confirmPassword;
  },

  canSubmit: function () {
    return state.isUsernameValid() && state.isPasswordValid() && state.isConfirmPasswordValid();
  }
};

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
              state.message !== "" ? m(".notification.is-warning", state.message) : null,
              m("form", {
                  onsubmit: function (event) {
                    event.preventDefault();
                    Auth.register(state.username, state.password).then(function () {
                      return Auth.login(state.username, state.password);
                    }).then(function () {
                      state.reset();
                      m.route.set("/home");
                    }).catch(function (error) {
                      state.password = "";
                      state.confirmPassword = "";
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
                          oninput: m.withAttr("value", function (username) {
                            state.username = username;
                          }),
                          onfocusout: function () {
                            state.validateUsername = true
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
                          oninput: m.withAttr("value", function (password) {
                            state.password = password;
                          }),
                          onfocusout: function () {
                            state.validatePassword = true;
                          },
                        })
                      )
                    ]
                  ),
                  m(".field",
                    [
                      m("label.label",
                        "Confirm password"
                      ),
                      m(".control",
                        m("input.input[type='password']", {
                          value: state.confirmPassword,
                          class: state.validateConfirmPassword && !state.isConfirmPasswordValid() ? "is-danger" : "",
                          oninput: m.withAttr("value", function (confirmPassword) {
                            state.confirmPassword = confirmPassword;
                          }),
                          onfocusout: function () {
                            state.validateConfirmPassword = true;
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
                        }, "Register"
                      )
                    )
                  ),
                  m(".field",
                    m(".control",
                      m("button.button[type='button']",
                        {
                          onclick: function () {
                            m.route.set("/login")
                          }
                        }, "Cancel"
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