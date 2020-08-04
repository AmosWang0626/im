/**
 * 全局参数
 */
let that = {
    last_message_id: "",
    ws: null,
    ws_url: null,
    badge: null // 用户当前状态(在线/离线/登录中/登录失败)
}

/**
 * 页面加载完成后执行
 */
$(function () {
    if (!"WebSocket" in window) {
        alert("您的浏览器不支持 WebSocket!")
        return
    }

    // 初始化参数
    that.badge = $("#badge")

    get("/server/ws", function (data) {
        that.ws_url = data
        console.info(that.ws_url)

        if (getUsername()) {
            // 初始化 WebSocket 连接
            initWebSocket()
        } else {
            // 初始化用户名
            initUsername()
        }
    })

    // 回车发送消息
    $("#message").keydown(function (e) {
        if (!e) e = window.event;

        let code = e.keyCode || e.which || 0;
        if (code === 13 && window.event) {
            e.returnValue = false;

            if (this.value && this.value.trim() !== "") {

                // 主动发送消息
                sendMessage(this.value)
            }

            // 清空输入框
            this.value = ""

        } else if (code === 13) {

            // 避免回车键换行
            e.preventDefault();
        }
    })

    // 发送消息
    $("#send").click(function () {
        const msg = $("#message");
        if (msg.val() && msg.val().trim() !== "") {
            // 主动发送消息
            sendMessage(msg.val())
        }
        // 清空输入框
        msg.val("")
    })

    // 退出
    that.badge.click(function () {
        const logoutModal = $('#logoutModal');
        logoutModal.modal()
        $("#logout").click(function () {
            del("/user/logout/" + getUsername())
            localStorage.clear()
            location.reload();
        })
    })
});

/**
 * 初始化用户名
 */
function initUsername() {
    let username = ""
    const usernameModal = $('#usernameModal');
    usernameModal.modal({backdrop: 'static', keyboard: false})
    usernameModal.on('hidden.bs.modal', function () {
        username = $(this).find('#init-username').val()

        if (!username) {
            usernameModal.modal({backdrop: 'static', keyboard: false})
        } else {
            username = username.trim()
            $("#username").innerHTML = username

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
                        location.reload();
                    }
                })
        }
    })
}

/**
 * 初始化 WebSocket 连接
 */
function initWebSocket() {
    that.ws = new WebSocket(that.ws_url);

    that.ws.onopen = function () {
        showStatus(0)

        const loginReq = {username: getUsername(), sender: getToken(), command: 1}
        that.ws.send(JSON.stringify(loginReq))
    };

    that.ws.onmessage = function (evt) {
        receiveMessage(evt.data);
    };

    that.ws.onerror = function () {
        showStatus(2)
    };

    that.ws.onclose = function () {
        showStatus(99)
    };
}

/**
 * 发送消息
 *
 * @param message 消息内容
 */
function sendMessage(message) {
    const selected = $("#users").val();
    let receiver = selected ? selected : 'admin'

    let params = {
        "createTime": new Date(),
        "message": message,
        "receiver": receiver,
        "sender": getToken(),
        "command": 3 // 发消息指令
    }

    that.ws.send(JSON.stringify(params));

    contentInnerHtml(true, message, getUsername());
}

/**
 * 处理接收到的消息
 *
 * @param message 消息内容
 */
function receiveMessage(message) {
    const body = JSON.parse(message);

    // 发送异常渲染异常信息
    if (!body.success) {
        $("#" + that.last_message_id).find('.notice').html(body.resMsg);
        return
    }

    switch (body.command) {
        case 2: // 登录
            $("#username").text(body.username)
            showStatus(1)
            break

        case 4: // 处理好友发送的消息
            contentInnerHtml(false, body.message, body.username);
            break

        case 98: // 在线用户
            const users = $("#users");
            const currentReceiver = users.val(); // 当前聊天人

            // 先清空在线用户
            users.empty();

            let finalLoginInfoArr = []
            body.loginInfoList.forEach(loginInfo => {
                // 当前聊天人优先排在第一个
                if (loginInfo.token === currentReceiver) {
                    users.append("<option value='" + loginInfo.token + "'>" + loginInfo.username + "</option>");
                    return
                }
                finalLoginInfoArr.push("<option value='" + loginInfo.token + "'>" + loginInfo.username + "</option>")
            })

            finalLoginInfoArr.forEach(value => {
                users.append(value)
            })
            break

        default:
            console.log(body)
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

    const _main = $("#main");
    _main.html(_main.html() + getMessageContent(current, sender, message, now, ''))

    // 滚动到最后一条消息
    document.getElementById(that.last_message_id).scrollIntoView()
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
    const id = "send_" + new Date().getTime();
    that.last_message_id = id

    let other =
        '<div id="${id}" class="media msg-item left">' +
        '   <img src="${avatar}" alt="avatar" class="align-self-center">' +
        '   <div class="media-body ml-1">' +
        '       <div class="row">' +
        '           <div class="col font12 color-username">${sender}</div>' +
        '       </div>' +
        '       <div class="row">' +
        '           <div class="col">' +
        '               <div class="msg-content left">${message}</div>' +
        '           </div>' +
        '       </div>' +
        '       <div class="row">' +
        '           <div class="col font12">' +
        '               <span class="color-red">${notice}</span>' +
        '               <span class="color-time">${time}</span>' +
        '           </div>' +
        '       </div>' +
        '   </div>' +
        '</div>';

    let me =
        '<div id="${id}" class="media msg-item right">' +
        '   <div class="media-body mr-1">' +
        '       <div class="row justify-content-end">' +
        '           <div class="col-md-auto">' +
        '               <div class="msg-content right current">${message}</div>' +
        '           </div>' +
        '       </div>' +
        '       <div class="row justify-content-end">' +
        '           <div class="col font12">' +
        '               <span class="color-red right">${notice}</span>' +
        '               <span class="color-time right">${time}</span>' +
        '           </div>' +
        '       </div>' +
        '   </div>' +
        '   <img src="${avatar}" alt="avatar">' +
        '</div>'

    let content = current ? me : other;
    content = content.replace("${id}", id)
    content = content.replace("${sender}", sender)
    content = content.replace("${avatar}", getAvatar(sender))
    content = content.replace("${message}", message)
    content = content.replace("${time}", time)
    content = content.replace("${notice}", notice)

    return content
}

/**
 * 显示用户状态
 *
 * @param flag default离线; 0登录中; 1在线; 2失败
 */
function showStatus(flag) {
    switch (flag) {
        case 0:
            that.badge.attr("class", "badge badge-light")
            that.badge.text("登录中")
            break

        case 1:
            that.badge.attr("class", "badge badge-success")
            that.badge.text("在线")
            break

        case 2:
            that.badge.attr("class", "badge badge-danger")
            that.badge.text("失败")
            break

        default:
            that.badge.attr("class", "badge badge-secondary")
            that.badge.text("离线")
            break
    }
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