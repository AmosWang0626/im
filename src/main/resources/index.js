let arr = []

let ws = null;

function contentInnerHtml(message) {
    arr.push(message)
    document.getElementById("content").innerHTML = arr.join("<br>")
}

function init() {
    if ("WebSocket" in window) {
        let ws_url = document.getElementById("ws_url").value;
        ws = new WebSocket(ws_url);

        ws.onopen = function () {
            contentInnerHtml("连接已打开");
        };

        ws.onmessage = function (evt) {
            contentInnerHtml("Ta：" + evt.data);
        };

        ws.onerror = function () {
            contentInnerHtml("连接失败，请检查 WS 地址");
        };

        ws.onclose = function () {
            contentInnerHtml("连接已关闭");
        };
    } else {
        contentInnerHtml("您的浏览器不支持 WebSocket!");
    }
}

function send() {
    let message = document.getElementById("message").value;

    ws.send(message);

    contentInnerHtml("Me：" + message);
}