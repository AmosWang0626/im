// 当前用户名或token
let username = ""
// 最后一个消息ID，用于自动滚动
let last_message_id = ""

// WebSocket 相关
let ws = null;
const ws_url = "ws://localhost:8090/ws/chat?token="

/**
 * 页面加载完成后执行
 */
window.onload = function () {
    if (!"WebSocket" in window) {
        alert("您的浏览器不支持 WebSocket!")
        return
    }

    initUsername()
    initWebSocket()

    const message = document.getElementById("message");
    message.onkeydown = function (e) {
        if (!e) e = window.event;

        let code = e.keyCode || e.which || 0;
        if (code === 13 && window.event) {
            e.returnValue = false;

            // 主动发送消息
            sendMessage(this.value)
            // 清空输入框
            this.value = ""
        } else if (code === 13) {
            // 避免回车键换行
            e.preventDefault();
        }
    }
}

/**
 * 初始化用户名
 */
function initUsername() {
    while (!username || username.trim().length === 0) {
        username = prompt("请输入用户名")
    }

    username = username.trim()
    document.getElementById("username").innerHTML = username
}

/**
 * 初始化 WebSocket 连接
 */
function initWebSocket() {
    ws = new WebSocket(ws_url + username);

    ws.onopen = function () {
        document.getElementById("title-status").className = "status-point status-online"
    };

    ws.onmessage = function (evt) {
        receiveMessage(evt.data);
    };

    ws.onerror = function () {
        document.getElementById("title-status").className = "status-point status-fail"
    };

    ws.onclose = function () {
        document.getElementById("title-status").className = "status-point status-offline"
    };
}

/**
 * 发送消息
 *
 * @param message 消息内容
 */
function sendMessage(message) {
    const tokens = document.getElementById("tokens");
    const selected = tokens.options[tokens.selectedIndex];
    let receiver = 'amos'
    if (selected) {
        receiver = selected.value
    } else {
        alert("当前用户都不在线")
    }


    let params = {
        "createTime": new Date(),
        "message": message,
        "receiver": receiver,
        "sender": username
    }

    ws.send(JSON.stringify(params));

    contentInnerHtml(true, message, username);
}

/**
 * 处理接收到的消息
 *
 * @param message 消息内容
 */
function receiveMessage(message) {
    const res = JSON.parse(message);
    if (!res.success) {
        // 发送异常渲染异常信息
        const lastMessageId = document.getElementById(last_message_id);
        lastMessageId.querySelector('#notice').innerHTML = res.message;

        return
    }

    const body = res.body;

    // 处理服务器主动推送的在线用户
    if (body.command === 15) {
        // 接收消息内的tokens
        const tokens = document.getElementById("tokens");
        tokens.options.length = 0;

        body.tokens.forEach(value => {
            const option = document.createElement("option");
            option.text = value;
            option.value = value;

            tokens.add(option)
        })
        return;
    }

    // 处理好友发送的消息
    contentInnerHtml(false, body.message, body.sender);
}

/**
 * 展示消息
 *
 * @param current [true我发出的 | false对方发出的]
 * @param message 消息内容
 * @param sender  发送人
 */
function contentInnerHtml(current, message, sender) {
    let now = new Date()
    now = now.getHours()
        + ":" + (now.getMinutes() > 9 ? now.getMinutes() : '0' + now.getMinutes())
        + ":" + (now.getSeconds() > 9 ? now.getSeconds() : '0' + now.getSeconds())

    document.getElementById("message-content")
        .innerHTML += getMessageContent(current, sender, message, now, '')

    // 滚动到最后一条消息
    document.getElementById(last_message_id).scrollIntoView()
}


/**
 * 获取 HTML 消息体
 *
 * @param current [true我发出的 | false对方发出的]
 * @param sender  发送人
 * @param message 消息内容
 * @param time    消息时间
 * @param notice  提示语
 */
function getMessageContent(current, sender, message, time, notice) {
    const id = "send_" + new Date().getMilliseconds();
    last_message_id = id

    let other = '<div id="${id}" class="container"><img src="${avatar}" alt="Avatar">' +
        '           <p>${message}</p>' +
        '           <span class="time-right">${time}</span>' +
        '           <span id="notice" class="notice-right">${notice}</span>' +
        '       </div>'
    let me = '<div id="${id}" class="container current"><img src="${avatar}" alt="Avatar" class="right">' +
        '            <p>${message}</p>' +
        '            <span class="time-left">${time}</span>' +
        '            <span id="notice" class="notice-left">${notice}</span>' +
        '        </div>'

    let content = current ? me : other;
    content = content.replace("${id}", id)
    content = content.replace("${avatar}", getAvatar(sender))
    content = content.replace("${message}", message)
    content = content.replace("${time}", time)
    content = content.replace("${notice}", notice)

    return content
}

/**
 * 根据名字获取头像
 *
 * @param username 名字
 */
function getAvatar(username) {
    const def = "../static/default.png"
    const boy = "../static/avatar_boy.png"
    const girl = "../static/avatar_girl.png"

    const number = new RegExp(/^\d+$/);
    if (number.test(username)) {
        return def;
    }

    const a = new RegExp(/^a.*$/);
    if (a.test(username)) {
        return boy
    }

    return girl;
}