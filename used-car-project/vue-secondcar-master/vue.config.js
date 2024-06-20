let proxyObj = {}


proxyObj['/ws'] = {
    ws: true,
    target: 'ws://localhost:8090'
};


// ws
module.exports = {
    devServer: {
        host: 'localhost',
        port: 8080,
        proxy: proxyObj
    }
}
