// createXHR
var createXHR = (function () {
    return new XMLHttpRequest();
})()

function doOnload(xhr, onSuccess, onError) {
    xhr.onload = function () {
        if ((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304) {
            if (typeof onSuccess === 'function') {
                onSuccess(xhr.responseText)
            }
        } else {
            console.error("服务器不想理你", xhr.status)
            if (typeof onSuccess === 'function') {
                onError(xhr.status)
            }
        }
    }
    xhr.onprogress = function (event) {
        if (event.lengthComputable) {
            console.info('>>>>' + event.position + event.totalSize)
        }
    }
}

function get(url, onSuccess, onError, sync = false) {
    const xhr = createXHR;
    doOnload(xhr, onSuccess, onError)
    xhr.open('get', url, sync)
    xhr.send(null)
}

function post(url, body, onSuccess, onError, sync = false) {
    const xhr = createXHR;
    doOnload(xhr, onSuccess, onError)
    xhr.open('post', url, sync)
    xhr.setRequestHeader("Content-Type", "application/json")
    xhr.send(body)
}

function del(url, body, onSuccess, onError, sync = false) {
    const xhr = createXHR;
    doOnload(xhr, onSuccess, onError)
    xhr.open('delete', url, sync)
    xhr.send(body)
}
