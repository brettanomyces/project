const m = require("mithril");

const Dummy = {
  dummy: function () {
    return m.request({
      method: "GET",
      url: "/api/dummy",
    })
  }
};

module.exports = Dummy;