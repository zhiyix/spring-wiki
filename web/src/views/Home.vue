<template>
  <a-breadcrumb style="margin: 16px 0">
    <a-breadcrumb-item>Home</a-breadcrumb-item>
    <a-breadcrumb-item>List</a-breadcrumb-item>
    <a-breadcrumb-item>App</a-breadcrumb-item>
  </a-breadcrumb>
  <a-layout-content
      :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
  >
    <pre>{{ebooks2}}</pre>
  </a-layout-content>
</template>

<script lang="ts">
import { defineComponent, ref, reactive, onMounted, toRef } from 'vue';
import axios from "axios";

export default defineComponent({
  name: 'Home',
  components: {
  },
  setup() {
    console.log("setup")
    const ebooks = ref()
    const ebooks2 = reactive({books: []})

    onMounted(() => {
      console.log("onMounted")
      axios.get("http://127.0.0.1:8081/ebook/list?name=Spring").then((response) => {
        ebooks.value = response.data
        ebooks2.books = response.data
        console.log(response)
      })
    })

    return {
      ebooks,
      ebooks2: toRef(ebooks2, "books")
    }
  }
})
</script>
