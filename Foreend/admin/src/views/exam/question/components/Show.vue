<template>
  <div style="line-height:1.8">
    <div v-if="qType==1" v-loading="qLoading">
      <div class="q-title" v-html="question.body"/>
      <div class="q-content">
          <span :key="item.id" v-for="item in question.selection" class="q-item-contain">
            <span class="q-item-prefix">{{item.prefix}}</span>
            <span v-html="item.content" class="q-item-content"></span>
          </span>
      </div>
    </div>
    <div v-else-if="qType==2" v-loading="qLoading">
      <div class="q-title" v-html="question.body"/>
      <div class="q-content">
          <span :key="item.id" v-for="item in question.selection" class="q-item-contain">
            <span class="q-item-prefix">{{item.prefix}}</span>
            <span v-html="item.content" class="q-item-content"></span>
          </span>
      </div>
    </div>
    <div v-else-if="qType==3" v-loading="qLoading">
      <div class="q-title" v-html="question.body" style="display: inline;margin-right: 10px"/>
      <span>（</span>
      <span :key="item.id" v-for="item in question.selection">
        <span v-html="item.content" class="q-item-content"></span>
       </span>
      <span>）</span>
    </div>
    <div v-else-if="qType==4" v-loading="qLoading">
      <div class="q-title" v-html="question.body"/>
    </div>
    <div v-else-if="qType==5" v-loading="qLoading">
      <div class="q-title" v-html="question.body"/>
    </div>
    <div v-else>
    </div>
  </div>

</template>

<script>
export default {
  name: 'QuestionShow',
  props: {
    question: {
      type: Object,
      default: function () {
        return {}
      }
    },
    qLoading: {
      type: Boolean,
      default: false
    },
    qType: {
      type: Number,
      default: 0
    }
  },
  methods: {}
}
</script>
