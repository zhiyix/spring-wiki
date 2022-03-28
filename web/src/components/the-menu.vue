<template>
  <a-layout-sider width="200" style="background: #fff">
    <a-menu
        mode="inline"
        :openKeys="openKeys"
        :style="{ height: '100%', borderRight: 0 }"
    >
      <a-menu-item key="welcome">
        <MailOutlined />
        <span>欢迎</span>
      </a-menu-item>
      <a-sub-menu v-for="item in level1" :key="item.id" :disabled="true">
      <template v-slot:title>
        <span><user-outlined />{{item.name}}</span>
      </template>
      <a-menu-item v-for="child in item.children" :key="child.id">
        <MailOutlined /><span>{{child.name}}</span>
      </a-menu-item>
      </a-sub-menu>
      <a-menu-item key="tip" :disabled="true">
        <span>以上菜单在分类管理配置</span>
      </a-menu-item>
    </a-menu>
  </a-layout-sider>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from 'axios';
import {message} from 'ant-design-vue';
import {tools} from "@/utils/tools";

export default defineComponent({
  name: 'TheMenu',
  props: {
    msg: String,
  },
  setup() {
    const level1 =  ref();
    let categorys: any;
    const openKeys =  ref();

    /**
     * 查询所有分类
     **/
    const handleQueryCategory = () => {
      axios.get("/ebook/category/all").then((response) => {
        categorys = response.data;
        console.log("原始数组：", categorys);

        // 加载完分类后，将侧边栏全部展开
        openKeys.value = [];
        for (let i = 0; i < categorys.length; i++) {
          openKeys.value.push(categorys[i].id)
        }

        level1.value = [];
        level1.value = tools.array2Tree(categorys, 0);
        console.log("树形结构：", level1.value);
      }).catch((error) => {
        message.error(error);
      });
    };

    onMounted(() => {
      handleQueryCategory();
    });

    return {
      level1,
      openKeys
    };
  },
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
