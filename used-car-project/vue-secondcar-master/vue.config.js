let proxyObj = {
    // '/api': {
    //     target: 'http://localhost:8090/api',
    //     changeOrigin: true,
    //     ws: true,
    //     pathRewrite: {
    //         '^/api': 'http://localhost:8090/api' //路径重写
    //     }
    // }
}


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
