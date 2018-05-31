const m = require("mithril");
const Auth = require("../models/Auth");

let state = {
  username: "",
  validateUsername: false,
  password: "",
  validatePassword: false,
  confirmPassword: "",
  validateConfirmPassword: false,
  email: "",
  validateEmail: false,
  message: "",

  reset: function () {
    this.username = "";
    this.validateUsername = false;
    this.password = "";
    this.validatePassword = false;
    this.confirmPassword = "";
    this.validateConfirmPassword = "";
    this.email = "";
    this.validateEmail = false;
    this.message = "";
  },

  isUsernameValid: function () {
    return this.username.length > 4;
  },

  isPasswordValid: function () {
    return this.password.length > 4;
  },

  isConfirmPasswordValid: function () {
    return this.isPasswordValid() && this.password === this.confirmPassword;
  },

  isEmailValid: function () {
    return /\S+@\S+/.test(this.email);
  },

  canSubmit: function () {
    return this.isUsernameValid() && this.isPasswordValid() && this.isConfirmPasswordValid();
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
              m("form", {
                  onsubmit: function (event) {
                    event.preventDefault();
                    Auth.register(state.username, state.password, state.email).then(function () {
                      return Auth.login(state.username, state.password);
                    }).then(function () {
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
                    [
                      m("label.label",
                        "Email"
                      ),
                      m(".control",
                        m("input.input[type='email']", {
                          value: state.email,
                          class: state.validateEmail && !state.isEmailValid() ? "is-danger" : "",
                          oninput: m.withAttr("value", function (email) {
                            state.email = email;
                          }),
                          onfocusout: function () {
                            state.validateEmail = true;
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