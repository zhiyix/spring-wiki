<template>
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
</template>

<script lang="ts">
import { defineComponent, ref, reactive, onMounted, toRef } from 'vue';
import axios from "axios";

export default defineComponent({
  name: 'Home',
  setup() {
    const actions: Record<string, string>[] = [
      { type: 'StarOutlined', text: '156' },
      { type: 'LikeOutlined', text: '156' },
      { type: 'MessageOutlined', text: '2' },
    ];
    console.log("setup")
    const ebooks = ref()
    const ebooks2 = reactive({books: []})

    onMounted(() => {
      console.log("onMounted")
      axios.get("/ebook/list?name=Spring").then((response) => {
        ebooks.value = response.data
        ebooks2.books = response.data
        console.log(response)
      })
    })

    return {
      actions,
      ebooks,
      ebooks2: toRef(ebooks2, "books")
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
