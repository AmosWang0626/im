// 最后一个消息ID，用于自动滚动
let last_message_id = ""

// WebSocket 相关
let ws = null;
let ws_url = "ws://"

/**
 * 页面加载完成后执行
 */
window.onload = function () {
    if (!"WebSocket" in window) {
        alert("您的浏览器不支持 WebSocket!")
        return
    }

    get("/server/ws", function (data) {
        ws_url = ws_url + data + "/ws"
        console.info('ws_url', ws_url)

        if (getToken()) {
            // 初始化 WebSocket 连接
            initWebSocket()
        } else {
            // 初始化用户名
            initUsername()
        }
    })

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
    let username = ""
    while (!username || username.trim().length === 0) {
        username = prompt("请输入用户名")
    }

    username = username.trim()
    document.getElementById("username").innerHTML = username

    post("/user/login",
        JSON.stringify({username: username, password: "123456"}),
        function (data) {
            const res = JSON.parse(data)
            if (res.success) {
                localStorage.setItem("token", res.token)
                localStorage.setItem("username", res.username)

                // 初始化 WebSocket 连接
                initWebSocket()
            } else {
                alert(res.resMsg)
            }
        })
}

/**
 * 初始化 WebSocket 连接
 */
function initWebSocket() {
    ws = new WebSocket(ws_url);

    ws.onopen = function () {
        document.getElementById("title-status").className = "status-point status-online"

        const loginReq = {username: getUsername(), sender: getToken(), command: 1}
        ws.send(JSON.stringify(loginReq))
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
    let receiver = 'admin'
    if (selected) {
        receiver = selected.value
    }


    let params = {
        "createTime": new Date(),
        "message": message,
        "receiver": receiver,
        "sender": getToken(),
        "command": 3 // 发消息指令
    }

    ws.send(JSON.stringify(params));

    contentInnerHtml(true, message, getUsername());
}

/**
 * 处理接收到的消息
 *
 * @param message 消息内容
 */
function receiveMessage(message) {
    const body = JSON.parse(message);
    console.log(body)

    // 发送异常渲染异常信息
    if (!body.success) {
        const lastMessageId = document.getElementById(last_message_id);
        lastMessageId.querySelector('#notice').innerHTML = body.resMsg;

        return
    }

    switch (body.command) {
        case 2: // 登录
            console.info(body.username + " 登录成功!", body.token)
            break

        case 98: // 在线用户
            // 接收消息内的tokens
            const tokens = document.getElementById("tokens");
            tokens.options.length = 0;

            body.loginInfoList.forEach(value => {
                const option = document.createElement("option");
                option.text = value.username;
                option.value = value.token;

                tokens.add(option)
            })
            break

        default:
            // 处理好友发送的消息
            contentInnerHtml(false, body.message, body.username);
            break
    }

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

function getToken() {
    return localStorage.getItem("token")
}

function getUsername() {
    return localStorage.getItem("username")
}