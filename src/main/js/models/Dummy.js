const m = require("mithril");

const Dummy = {
  dummy: function () {
    m.request({
      method: "GET",
      url: "/api/dummy",
    }).then(function (result) {
      console.log(result)
    })
  }
};

module.exports = Dummy;