const m = require("mithril");

const Auth = {
  authenticated: false,

  login: function (username, password) {
    return m.request({
      method: "POST",
      url: "/auth/login",
      data: {
        "username": username,
        "password": password
      }
    }).then(function () {
      Auth.authenticated = true;
    })
  },

  logout: function () {
    m.request({
      method: "POST",
      url: "/auth/logout",
    }).then(function () {
      Auth.authenticated = false;
    }).finally(function() {
      m.route.set("/login");
    })
  },

  register: function (username, password) {
    return m.request({
      method: "POST",
      url: "/auth/register",
      data: {
        "username": username,
        "password": password
      }
    })
  }
};

module.exports = Auth;