<template>
  <a-layout>
    <the-header></the-header>
    <a-layout>
      <the-menu></the-menu>
      <a-layout style="padding: 0 24px 24px">
        <router-view/>
      </a-layout>
    </a-layout>
    <the-footer></the-footer>
  </a-layout>
</template>
<script lang="ts">
import { UserOutlined, LaptopOutlined, NotificationOutlined } from '@ant-design/icons-vue';
import { defineComponent } from 'vue';
import TheMenu from '@/components/the-menu.vue';
import TheHeader from '@/components/the-header.vue';
import TheFooter from '@/components/the-footer.vue';

export default defineComponent({
  components: {
    UserOutlined,
    LaptopOutlined,
    NotificationOutlined,
    TheMenu,
    TheHeader,
    TheFooter
  }
});
</script>
<style>
#components-layout-demo-top-side-2 .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side-2 .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

.site-layout-background {
  background: #fff;
}
</style>
