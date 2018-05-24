const m = require("mithril");

const Auth = {
  username: "",
  validateUsername: false,
  password: "",
  validatePassword: false,
  message: "",

  reset: function() {
    Auth.username = "";
    Auth.validateUsername = false;
    Auth.password = "";
    Auth.validatePassword = false;
    Auth.message = "";
  },

  setUsername: function (value) {
    Auth.username = value
  },

  setValidateUsername: function(validate) {
    Auth.validateUsername = validate
  },

  isUsernameValid: function() {
    return Auth.username !== ""
  },

  setPassword: function (value) {
    Auth.password = value
  },

  setValidatePassword: function(validate) {
    Auth.validatePassword = validate
  },

  isPasswordValid: function() {
    return Auth.password !== ""
  },

  canSubmit: function() {
    return Auth.isUsernameValid() && Auth.isPasswordValid()
  },

  login: function () {
    m.request({
      method: "POST",
      url: "/auth/login",
      data: {
        "username": Auth.username,
        "password": Auth.password,
      }
    })
      .then(function() {
        Auth.reset();
        m.route.set("home")
      })
      .catch(function (e) {
        Auth.password = "";
        Auth.message = e.message;
      })
  },

  logout: function () {
    m.request({
      method: "POST",
      url: "/auth/logout",
    })
      .finally(function() {
        m.route.set("/login");
      })
  }
};

module.exports = Auth;