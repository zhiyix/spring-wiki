<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
        <a-form layout="inline" :model="param">
          <a-form-item>
            <a-button type="primary" @click="handleQuery()">
              查询
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="add()">
              新增
            </a-button>
          </a-form-item>
        </a-form>
      </p>
      <p>
        <a-alert
            class="tip"
            message="小提示：这里的文档会显示到首页的侧边菜单"
            type="info"
            closable
        />
      </p>
      <a-table
          v-if="level1.length > 0"
          :columns="columns"
          :row-key="record => record.id"
          :data-source="level1"
          :loading="loading"
          :pagination="false"
          :defaultExpandAllRows="true"
      >
        <template #cover="{ text: cover }">
          <img v-if="cover" :src="cover" alt="avatar" />
        </template>
        <template v-slot:action="{ text, record }">
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
              编辑
            </a-button>
            <a-popconfirm
                title="删除后不可恢复，确认删除?"
                ok-text="是"
                cancel-text="否"
                @confirm="handleDelete(record.id)"
            >
              <a-button type="danger">
                删除
              </a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>

  <a-modal
      title="文档表单"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
    <a-form :model="document" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="名称">
        <a-input v-model:value="document.name" />
      </a-form-item>
      <a-form-item label="父文档">
        <a-select
            v-model:value="document.parent"
            ref="select"
        >
          <a-select-option :value="0">
            无
          </a-select-option>
          <a-select-option v-for="c in level1" :key="c.id" :value="c.id" :disabled="document.id === c.id">
            {{c.name}}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="顺序">
        <a-input v-model:value="document.sort" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import axios from 'axios';
import { message } from 'ant-design-vue';
import { tools } from "@/utils/tools";

export default defineComponent({
  name: 'AdminDocument',
  setup() {
    const param = ref();
    param.value = {};
    const documents = ref();
    const loading = ref(false);

    const columns = [
      {
        title: '名称',
        dataIndex: 'name'
      },
      // {
      //   title: '父文档',
      //   key: 'parent',
      //   dataIndex: 'parent'
      // },
      {
        title: '顺序',
        dataIndex: 'sort'
      },
      {
        title: 'Action',
        key: 'action',
        slots: { customRender: 'action' }
      }
    ];

    /**
     * 一级文档树，children属性就是二级文档
     * [{
     *   id: "",
     *   name: "",
     *   children: [{
     *     id: "",
     *     name: "",
     *   }]
     * }]
     */
    const level1 = ref(); // 一级文档树，children属性就是二级文档
    level1.value = [];

    /**
     * 数据查询
     **/
    const handleQuery = () => {
      loading.value = true;
      // 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑，则列表显示的还是编辑前的数据
      level1.value = [];
      axios.get("/ebook/document/all").then((response) => {
        loading.value = false;
        const data = response.data;
        documents.value = data;
        console.log("原始数组：", documents.value);

        level1.value = [];
        level1.value = tools.array2Tree(documents.value, 0);
        console.log("树形结构：", level1);
      }).catch((error) => {
        message.error(error);
      });
    };

    // -------- 表单 ---------
    const document = ref({});
    const modalVisible = ref(false);
    const modalLoading = ref(false);
    const handleModalOk = () => {
      modalLoading.value = true;
      axios.post("/ebook/document/save", document.value).then((response) => {
        modalLoading.value = false;
        modalVisible.value = false;

        // 重新加载列表
        handleQuery();
      }).catch((error) => {
        message.error(error);
      });
    };

    /**
     * 编辑
     */
    const edit = (record: any) => {
      modalVisible.value = true;
      document.value = tools.copy(record);
    };

    /**
     * 新增
     */
    const add = () => {
      modalVisible.value = true;
      document.value = {};
    };

    const handleDelete = (id: number) => {
      axios.delete("/ebook/document/delete/" + id).then((response) => {
        const data = response.data; // data = commonResp
        // 重新加载列表
        handleQuery();
      }).catch((error) => {
        message.error(error);
      });
    };

    onMounted(() => {
      handleQuery();
    });

    return {
      param,
      // documents,
      level1,
      columns,
      loading,
      handleQuery,

      edit,
      add,

      document,
      modalVisible,
      modalLoading,
      handleModalOk,

      handleDelete
    }
  }
});
</script>

<style scoped>
img {
  width: 50px;
  height: 50px;
}
</style>
