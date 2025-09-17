# DataSyncUtil 数据同步工具类使用说明

## 概述

`DataSyncUtil` 是一个通用的数据同步工具类，能够处理任意数据接口对象和实体对象。它提供了三个核心方法来完成数据同步功能。

## 核心类

### KeyUtil

负责生成数据的唯一标识Key，用于数据同步对比。

- `KeyUtil.key(Object obj)`: 根据对象的所有字段值生成唯一标识

### DataSyncUtil

提供数据同步的核心功能：

1. `getDataToInsert(Collection<T> apiDataList, Collection<T> dbDataList)`: 获取需要新增到数据库的数据
2. `getDataToUpdate(Collection<T> apiDataList, Collection<T> dbDataList)`: 获取需要更新的数据库数据
3. `getDataToDelete(Collection<T> apiDataList, Collection<T> dbDataList)`: 获取需要删除的数据库数据

## 使用方式

### 基本用法

```java
import org.dromara.okx.util.DataSyncUtil;
import org.dromara.okx.util.KeyUtil;

// 1. 准备数据
List<YourEntity> apiData = getApiData();      // 从API获取的数据
List<YourEntity> dbData = getDbData();        // 从数据库获取的现有数据

// 2. 获取需要同步的数据
List<YourEntity> toInsert = DataSyncUtil.getDataToInsert(apiData, dbData);
List<YourEntity> toUpdate = DataSyncUtil.getDataToUpdate(apiData, dbData);  
List<YourEntity> toDelete = DataSyncUtil.getDataToDelete(apiData, dbData);

// 3. 执行同步操作
insertBatch(toInsert);    // 批量插入
updateBatch(toUpdate);    // 批量更新
deleteBatch(toDelete);    // 批量删除
```

### 实际应用示例

以OKX交易对数据同步为例：

```java
@Service
public class InstrumentSyncService {
    
    @Autowired
    private InstrumentMapper instrumentMapper;
    
    @Autowired
    private OkxApiService okxApiService;
    
    @Transactional
    public void syncInstruments() {
        // 1. 获取API数据
        List<InstrumentEntity> apiData = okxApiService.getInstruments();
        
        // 2. 获取数据库数据
        List<InstrumentEntity> dbData = instrumentMapper.selectList(null);
        
        // 3. 数据同步分析
        List<InstrumentEntity> toInsert = DataSyncUtil.getDataToInsert(apiData, dbData);
        List<InstrumentEntity> toUpdate = DataSyncUtil.getDataToUpdate(apiData, dbData);
        List<InstrumentEntity> toDelete = DataSyncUtil.getDataToDelete(apiData, dbData);
        
        // 4. 执行数据库操作
        if (!toInsert.isEmpty()) {
            instrumentMapper.insertBatch(toInsert);
            log.info("新增交易对数据: {} 条", toInsert.size());
        }
        
        if (!toUpdate.isEmpty()) {
            toUpdate.forEach(instrumentMapper::updateById);
            log.info("更新交易对数据: {} 条", toUpdate.size());
        }
        
        if (!toDelete.isEmpty()) {
            List<Long> idsToDelete = toDelete.stream()
                    .map(InstrumentEntity::getId)
                    .collect(Collectors.toList());
            instrumentMapper.deleteBatchIds(idsToDelete);
            log.info("删除交易对数据: {} 条", toDelete.size());
        }
    }
}
```

## Key生成原理

`KeyUtil.key(Object obj)` 方法的工作原理：

1. 获取对象的所有字段
2. 按字段名排序确保一致性
3. 将字段名和字段值组合成字符串
4. 使用"|"分隔符连接所有字段信息

示例：
```java
InstrumentEntity entity = new InstrumentEntity("BTC-USDT", "SPOT", "live");
String key = KeyUtil.key(entity);
// 结果: "baseCcy:BTC|instId:BTC-USDT|instType:SPOT|state:live"
```

## 优势特性

1. **通用性**: 支持任意Java对象，无需特殊接口或注解
2. **类型安全**: 使用泛型确保类型一致性
3. **空值处理**: 优雅处理null对象和空集合
4. **一致性**: 相同内容的对象始终生成相同的Key
5. **简单易用**: API简洁，易于理解和使用

## 注意事项

1. 确保用于比较的对象类型相同
2. Key是基于所有字段生成的，字段值的微小变化都会影响比较结果
3. 对于大量数据的同步，建议分批处理以避免内存问题
4. 数据库操作应该在事务中执行以保证一致性

## 测试

项目包含完整的单元测试：
- `KeyUtilTest`: 测试Key生成功能
- `DataSyncUtilTest`: 测试数据同步功能
- `DataSyncUtilExample`: 提供完整的使用示例