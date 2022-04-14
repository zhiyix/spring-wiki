<template>
  <a-layout>
    <a-layout-sider width="200" style="background: #fff">
      <a-menu
          mode="inline"
          :style="{ height: '100%', borderRight: 0 }"
          @click="handleClick"
          :openKeys="openKeys"
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
    <a-layout style="padding: 0 24px 24px">
      <a-breadcrumb style="margin: 16px 0">
        <a-breadcrumb-item>Home</a-breadcrumb-item>
        <a-breadcrumb-item>List</a-breadcrumb-item>
        <a-breadcrumb-item>App</a-breadcrumb-item>
      </a-breadcrumb>
      <a-layout-content
          :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
      >
        <a-list item-layout="vertical" size="large" :pagination="pagination" :grid="{ gutter: 20, column: 3 }" :data-source="ebooks">
          <template #footer>
            <div>
              <b>ant design vue</b>
              footer part
            </div>
          </template>
          <template #renderItem="{ item }">
            <a-list-item key="item.name">
              <template #actions>
            <span v-for="{ type, text } in actions" :key="type">
              <component v-bind:is="type" style="margin-right: 8px" />
              {{ text }}
            </span>
              </template>
              <template #extra>
                <img
                    width="64"
                    alt="logo"
                    src="https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                />
              </template>
              <a-list-item-meta :description="item.description">
                <template #title>
                  <a :href="item.href">{{ item.name }}</a>
                </template>
                <template #avatar><a-avatar :src="item.cover" /></template>
              </a-list-item-meta>
              {{ item.content }}
            </a-list-item>
          </template>
        </a-list>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script lang="ts">
import { defineComponent, ref, reactive, onMounted, toRef } from 'vue';
import TheMenu from '@/components/the-menu.vue';
import axios from "axios";
import { message } from 'ant-design-vue';
import {tools} from "@/utils/tools";

export default defineComponent({
  name: 'Home',
  components: {
    TheMenu,
  },
  setup() {
    const openKeys =  ref();
    const actions: Record<string, string>[] = [
      { type: 'StarOutlined', text: '156' },
      { type: 'LikeOutlined', text: '156' },
      { type: 'MessageOutlined', text: '2' },
    ];
    console.log("setup")

    const level1 =  ref();
    let categorys: any;
    /**
     * 查询所有分类
     **/
    const handleQueryCategory = () => {
      axios.get("/ebook/category/all").then((response) => {
        const data = categorys = response.data;
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

    const isShowWelcome = ref(true);
    let categoryId2 = 0;

    const handleQueryEbook = () => {
      axios.get("/ebook/list", {
        params: {
          page: 1,
          size: 1000,
          categoryId2: categoryId2
        }
      }).then((response) => {
        const data = response.data;
        ebooks.value = data.list;
        // ebooks1.books = data;
      }).catch((error) => {
        message.error(error);
      });
    };


    const handleClick = (value: any) => {
      // console.log("menu click", value)
      if (value.key === 'welcome') {
        isShowWelcome.value = true;
      } else {
        categoryId2 = value.key;
        isShowWelcome.value = false;
        handleQueryEbook();
      }
      // isShowWelcome.value = value.key === 'welcome';
    };
    
    const ebooks = ref()
    const ebooks2 = reactive({books: []})

    onMounted(() => {
      handleQueryCategory();
      // handleQueryEbook();
    })

    return {
      actions,
      ebooks,
      ebooks2: toRef(ebooks2, "books"),
      
      handleClick,
      level1,

      isShowWelcome,

      openKeys
    }
  }
})
</script>

<style scoped>
.ant-avatar {
  width: 50px;
  height: 50px;
  line-height: 50px;
  border-radius: 8%;
  margin: 5px 0;
}
</style>
