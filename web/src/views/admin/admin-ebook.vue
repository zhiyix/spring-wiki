<template>
  <a-layout-content
      :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
  >
    <p>
      <a-form layout="inline" :model="param">
        <a-form-item>
          <a-input v-model:value="param.name" placeholder="名称">
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="handleQuery({page: 1, size: pagination.pageSize})">
            查询
          </a-button>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="handleAdd()">
            新增
          </a-button>
        </a-form-item>
      </a-form>
    </p>
    <a-table
        :columns="columns"
        :row-key="record => record.id"
        :data-source="ebooks"
        :pagination="pagination"
        :loading="loading"
        @change="handleTableChange"
    >
      <template #cover="{ text: cover }">
        <img v-if="cover" :src="cover" alt="avatar" />
      </template>
      <template v-slot:category="{ text, record }">
        <span>{{ getCategoryName(record.category1Id) }} / {{ getCategoryName(record.category2Id) }}</span>
      </template>
      <template v-slot:action="{ text, record }">
        <a-space size="small">
          <a-button type="primary" @click="handleEdit(record)">
            编辑
          </a-button>
          <a-popconfirm
              title="删除后不可恢复，确认删除?"
              ok-text="是"
              cancel-text="否"
              @confirm="handleDelete(record)"
          >
            <a-button type="danger">
              删除
            </a-button>
          </a-popconfirm>
        </a-space>
      </template>
    </a-table>
  </a-layout-content>
  <a-modal
      title="Title"
      v-model:visible="visible"
      :confirm-loading="confirmLoading"
      @ok="handleOk"
  >
    <a-form :model="ebook" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="封面">
        <a-input v-model:value="ebook.cover" />
      </a-form-item>
      <a-form-item label="名称">
        <a-input v-model:value="ebook.name" />
      </a-form-item>
      <a-form-item label="分类">
        <a-cascader
            v-model:value="categoryIds"
            :field-names="{ label: 'name', value: 'id', children: 'children' }"
            :options="level1"
        />
      </a-form-item>
      <a-form-item label="描述">
        <a-input v-model:value="ebook.description" type="textarea" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts">
import { StarOutlined, LikeOutlined, MessageOutlined } from '@ant-design/icons-vue';
import { defineComponent, ref, onMounted } from 'vue';
import axios from 'axios';
import { message } from 'ant-design-vue';
import { tools } from '@/utils/tools'

export default defineComponent({
  components: {
    StarOutlined,
    LikeOutlined,
    MessageOutlined,
  },
  setup() {
    const param = ref();
    param.value = {};
    const ebooks = ref();
    const pagination = ref({
      current: 1,
      pageSize: 5,
      total: 0
    });
    const loading = ref(false);
    const columns = [
      {
        title: '封面',
        dataIndex: 'cover',
        slots: { customRender: 'cover' }
      },
      {
        title: '名称',
        dataIndex: 'name'
      },
      {
        title: '分类',
        slots: { customRender: 'category' }
      },
      {
        title: '文档数',
        dataIndex: 'docCount'
      },
      {
        title: '阅读数',
        dataIndex: 'viewCount'
      },
      {
        title: '点赞数',
        dataIndex: 'voteCount'
      },
      {
        title: 'Action',
        key: 'action',
        slots: { customRender: 'action' }
      }
    ];

    /**
     * 数据查询
     **/
    const handleQuery = (params: any) => {
      loading.value = true;
      // 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑，则列表显示的还是编辑前的数据
      ebooks.value = [];
      axios.get("/ebook/list", {
        params: {
          page: params.page,
          size: params.size,
          name: param.value.name
        }
      }).then((response) => {
        loading.value = false;
        const data = response.data;
        ebooks.value = data.list;

        // 重置分页按钮
        pagination.value.current = params.page;
        pagination.value.total = data.total;
      });
    };

    /**
     * 表格点击页码时触发
     */
    const handleTableChange = (pagination: any) => {
      console.log("看看自带的分页参数都有啥：" + pagination);
      handleQuery({
        page: pagination.current,
        size: pagination.pageSize
      });
    };

    /////////////////////////////////////// 模态 ////////////////////////////////////////////////////////////////////////
    // modal dialog
    // -------- 表单 ---------
    /**
     * 数组，[100, 101]对应：前端开发 / Vue
     */
    const categoryIds = ref();
    const level1 =  ref();
    const ebook = ref();
    const modalText = ref<string>('Content of the modal');
    const visible = ref<boolean>(false);
    const confirmLoading = ref<boolean>(false);
    /**
     * 编辑
     */
    const handleEdit = (record: any) => {
      ebook.value = tools.copy(record);
      visible.value = true;
      categoryIds.value = [ebook.value.category1Id, ebook.value.category2Id]
    };

    /**
     * 新增
     */
    const handleAdd = () => {
      ebook.value = {}
      visible.value = true;
    }
    /**
     * 删除
     */
    const handleDelete = (record: any) => {
      axios.delete("/ebook/delete/" + record.id).then((response) => {
        // 重新加载列表
        handleQuery({
          page: pagination.value.current,
          size: pagination.value.pageSize,
        });
      });
    }
    const showModal = () => {
      visible.value = true;
    };
    const handleOk = () => {
      modalText.value = 'The modal will be closed after two seconds';
      confirmLoading.value = true;
      ebook.value.category1Id = categoryIds.value[0];
      ebook.value.category2Id = categoryIds.value[1];
      axios.post("/ebook/save", ebook.value).then((response) => {
        confirmLoading.value = false;

        visible.value = false;

        // 重新加载列表
        handleQuery({
          page: pagination.value.current,
          size: pagination.value.pageSize,
        });
      });
    };

    let categorys: any;
    /**
     * 查询所有分类
     **/
    const handleQueryCategory = () => {
      loading.value = true;
      axios.get("/ebook/category/all").then((response) => {
        loading.value = false;
        const data = response.data;
        categorys = data;
        console.log("原始数组：", categorys);

        level1.value = [];
        level1.value = tools.array2Tree(categorys, 0);
        console.log("树形结构：", level1.value);

        // 加载完分类后，再加载电子书，否则如果分类树加载很慢，则电子书渲染会报错
        handleQuery({
          page: 1,
          size: pagination.value.pageSize,
        });
      }).catch((error) => {
        message.error(error);
      });
    };

    const getCategoryName = (cid: number) => {
      // console.log(cid)
      let result = "";
      categorys.forEach((item: any) => {
        if (item.id === cid) {
          // return item.name; // 注意，这里直接return不起作用
          result = item.name;
        }
      });
      return result;
    };

    onMounted(() => {
      handleQueryCategory();
      handleQuery({
        page: pagination.value.current,
        size: pagination.value.pageSize,
      });
    })

    return {
      param,
      ebooks,
      pagination,
      columns,
      loading,
      handleTableChange,

      // modal dialog
      categoryIds,
      level1,
      ebook,
      handleQuery,
      handleAdd,
      handleEdit,
      handleDelete,
      modalText,
      visible,
      confirmLoading,
      showModal,
      handleOk,
      getCategoryName,
    };
  },
});
</script>