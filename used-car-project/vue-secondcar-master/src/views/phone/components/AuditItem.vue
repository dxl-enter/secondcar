<template>
  <div @click="onGotoInfo" class="film-item">
    <img :src="info.carPicture || 'http://v1.img.360kuai.com/video/320_180_/t0101a908ee7a9848cf.jpg?size=1920x1080'" alt="">
    <div class="film-information-container">
      <div class="film-actors-container">
        {{info.carBrand}}
        {{info.carSeries}}
        {{info.carType}}
      </div>
      <div class="film-actors-price">￥{{info.carPrice}}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AuditItem',
  props:{
    item:{
      type:Object,
      default:()=>{}
    }
  },
  watch:{
    item:{
      deep:true,
      immediate:true,
      handler:function(newVal){
        console.log('newVal===================',newVal)
        if(newVal){
          this.info = newVal
        }
      }
    }
  },
  data(){
    return{
      info:{}
    }
  },
  methods:{
    onGotoInfo(){
      this.$emit('goToDetails', this.info)
    }
  }
}
</script>

<style lang="less" scoped>
.film-item {
  width: 100%;
  flex-grow: 1;
  break-inside: avoid-column;
  background: white;
  border-radius: 0.35rem;
  box-shadow: 0 0 0 0.05rem #e7e7e7;
  overflow: hidden;
  margin-bottom: 0.6rem;
  img {
    width: 100%;
    object-fit: cover;
    border-radius: 0.35rem 0.35rem 0 0;
  }
  .film-information-container {
    margin: 0.45rem 0.25rem;
    .film-actors-container {
      font-size: 0.7rem;
      word-break: break-all;
      text-overflow: ellipsis;
      display: -webkit-box;
      overflow: hidden;
      margin: 0 0 0.5rem 0;
    }
    .film-actors-price {
      font-size: 1rem;
      font-weight: bolder;
      color: #3196fa;
    }
  }
}
</style>
