const m = require("mithril");

const Auth = {
  username: "",
  validateUsername: false,
  password: "",
  validatePassword: false,

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

  login: function () {
    m.request({
      method: "POST",
      url: "/auth/login",
      data: {
        "username": Auth.username,
        "password": Auth.password,
      }
    })
      .then(function(result) {
        m.route.set("home")
      })
      .catch(function (e) {
        console.error("catch");
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