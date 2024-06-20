
//===============方法一、简单方式================================================
/* 方法一简单方式//封装操作localStorage本地储存的方法

var storage = {

    set(key, value){
        localStorage.setItem(key,JSON.stringify(value));
    },
    get(key){
        return JSON.parse(localStorage.getItem(key));
    },
    remove(key){
        localStorage.removeItem(key);
    }

};

export default storage;
*/



//===============方法二、全面设置、过期时间、key、namespace================================================
/**
 * 方法二、全面设置、过期时间、key、namespace
 * localStorage 本地缓存功能
 */
/* 方法二的使用方式
    1. 导入 import cache from '@/utils/cache.js'
    let data = {
        name: 'Justicky',
        work: 'front end'
    }
    2. 设置数据
    cache.set('myInfo', data, 4 * 60 * 60)   // 缓存有效期4小时
    3. 获取数据
    cache.get('myInfo') // 若已过期返回false，没过期返回相应数据
    4. 移除数据
    cache.remove('myInfo') // 若操作失败返回false，否则为true
    5. 清空数据
    cache.clear() // 若操作失败返回false，否则为true
*/
class Cache {
    prefix = 'app-name'; // 项目前缀，根据不同项目而定
    expires = 4 * 60 * 60; // 默认四小时后失效
    /**
     * [constructor description]
     * @param  {String} prefix  项目前缀
     * @param  {Number} expires 默认缓存时间单位秒
     * @return {[type]}         [description]
     */
    constructor(prefix, expires) {
        if (prefix) {
            this.prefix = prefix.toString()
        }
        if (expires) {
            this.expires = parseInt(expires, 10)
        }
    }

    /**
     * 设置缓存
     * @param key           缓存名
     * @param value         缓存值
     * @param expires       缓存有效时间
     * @returns {boolean}   成功：true, 失败：false
     */
    set(key, value, expires) {
        // 非法缓存名过滤
        if (key === undefined || key === '' || typeof key === 'object') {
            console.error('设置的缓存名不合法！')
            return false
        }

        // 非法缓存时间过滤
        if (!expires || typeof expires !== 'number') {
            console.error('设置缓存过期时间不合法，已使用默认缓存时间！')
            expires = this.expires
        } else {
            expires = parseInt(expires, 10)
        }

        // localStorage中存储数据的键值
        let cacheName = this.prefix + '-[' + key.toString() + ']'
        // 获取过期到秒的时间戳
        let expiresTime = Date.parse(new Date()) / 1000 + expires
        // 初始化存储数据
        const data = {
            type: typeof value,
            value: value,
            expires: expiresTime
        };

        // 存入localStorage
        try {
            window.localStorage.setItem(cacheName, JSON.stringify(data))
            return true
        } catch (err) {
            console.error('存储失败：' + JSON.stringify(err))
            return false
        }
    }

    /**
     * 读取缓存内容
     * @param key
     * @returns {*}
     */
    get(key) {
        // 获取当前到秒的时间戳，用于判断是否过期
        let currentTime = Date.parse(new Date()) / 1000

        // 非法缓存名过滤
        if (key === undefined || key === '' || typeof key === 'object') {
            console.error('读取的缓存名不合法！')
            return false
        }

        // 获取localStorage中要读取的缓存名
        let cacheName = this.prefix + '-[' + key.toString() + ']'
        try {
            const cacheData = JSON.parse(window.localStorage.getItem(cacheName))
            // 判断判断缓存是否过期
            if (cacheData.expires < currentTime) {
                console.error('读取的缓存数据已过期!')
                return false
            }
            return cacheData.value
        } catch (err) {
            console.error('读取数据失败：' + JSON.stringify(err))
            return false
        }
    }

    /**
     * 移除一个缓存
     * @param key
     * @returns {boolean}
     */
    remove(key) {
        // 非法缓存名过滤
        if (key === undefined || key === '' || typeof key === 'object') {
            console.error('移除的缓存名不合法！')
            return false
        }
        let cacheName = this.prefix + '-[' + key.toString() + ']'
        try {
            window.localStorage.removeItem(cacheName)
            return true
        } catch (err) {
            console.error('移除指定缓存数据失败：' + JSON.stringify(err))
            return false
        }
    }

    /**
     * 清空所有缓存数据
     */
    clear() {
        try {
            window.localStorage.clear()
            return true
        } catch (err) {
            console.error('清空所有缓存数据失败：' + JSON.stringify(err))
            return false
        }
    }
}

const storage = new Cache();
export default storage






//===============方法三 简单设置过期时间================================================
/* // 方法三 简单设置过期时间
// 封装一个localStorage函数,减少页面请求
export function Storage() {
    if (!window.localStorage) {
        // 这里应该走cookie逻辑
        alert('此版本浏览器不支持本地存储')
        return false
    }
    return {
        set(key, obj, outtime) {
            let ctime = parseInt(Date.now() / 1000) //获取存数据的时间
            let exp = outtime || 24 * 60 * 60//outtime 是 秒为单位的过期时间
            let outObj = { // 时间和数据
                outime: ctime + exp,
                data: obj
            }
            localStorage.setItem(key, JSON.stringify(outObj))
        },
        get(key) {
            let data = JSON.parse(localStorage.getItem(key))
            //初始化没有数据
            if (!data) { return false }
            //判断过期时间 和获取数据的时间对比 大于过期时间说明超时
            if (data.outime >= parseInt(Date.now() / 1000)) {
                return data.data
            } else {
                return false
            }
        }
    }
}



 */
