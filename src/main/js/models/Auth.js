const m = require("mithril");

const Auth = {
  username: "",
  password: "",

  setUsername: function (value) {
    Auth.username = value
  },
  setPassword: function (value) {
    Auth.password = value
  },
  canSubmit: function () {
    return Auth.username !== "" && Auth.password !== ""
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