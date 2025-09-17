package org.dromara.okx.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * DataSyncUtil使用示例
 * 展示如何使用DataSyncUtil进行数据同步操作
 * 
 * @author Generated
 */
public class DataSyncUtilExample {

    /**
     * 示例实体类 - 代表API接口返回的数据或数据库实体
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InstrumentData {
        private String instId;    // 产品ID
        private String instType;  // 产品类型
        private String baseCcy;   // 交易币种
        private String quoteCcy;  // 计价币种
        private String state;     // 产品状态
    }

    /**
     * 数据同步使用示例
     * 演示如何使用DataSyncUtil处理API数据和数据库数据的同步
     */
    public static void main(String[] args) {
        System.out.println("=== DataSyncUtil使用示例 ===");

        // 模拟API接口返回的数据
        List<InstrumentData> apiDataList = Arrays.asList(
                new InstrumentData("BTC-USDT", "SPOT", "BTC", "USDT", "live"),
                new InstrumentData("ETH-USDT", "SPOT", "ETH", "USDT", "live"),
                new InstrumentData("BNB-USDT", "SPOT", "BNB", "USDT", "live"),
                new InstrumentData("ADA-USDT", "SPOT", "ADA", "USDT", "suspend") // 新的状态
        );

        // 模拟数据库中已存在的数据
        List<InstrumentData> dbDataList = Arrays.asList(
                new InstrumentData("BTC-USDT", "SPOT", "BTC", "USDT", "live"),     // 相同，无需变化
                new InstrumentData("ETH-USDT", "SPOT", "ETH", "USDT", "suspend"),  // 状态不同，需要更新
                new InstrumentData("SOL-USDT", "SPOT", "SOL", "USDT", "live")      // API中没有，需要删除
        );

        System.out.println("API数据:");
        apiDataList.forEach(data -> System.out.println("  " + data));

        System.out.println("\n数据库数据:");
        dbDataList.forEach(data -> System.out.println("  " + data));

        // 获取需要新增的数据
        List<InstrumentData> dataToInsert = DataSyncUtil.getDataToInsert(apiDataList, dbDataList);
        System.out.println("\n需要新增的数据 (" + dataToInsert.size() + "条):");
        dataToInsert.forEach(data -> System.out.println("  INSERT: " + data));

        // 获取需要更新的数据
        List<InstrumentData> dataToUpdate = DataSyncUtil.getDataToUpdate(apiDataList, dbDataList);
        System.out.println("\n需要更新的数据 (" + dataToUpdate.size() + "条):");
        dataToUpdate.forEach(data -> System.out.println("  UPDATE: " + data));

        // 获取需要删除的数据
        List<InstrumentData> dataToDelete = DataSyncUtil.getDataToDelete(apiDataList, dbDataList);
        System.out.println("\n需要删除的数据 (" + dataToDelete.size() + "条):");
        dataToDelete.forEach(data -> System.out.println("  DELETE: " + data));

        System.out.println("\n=== 同步操作完成 ===");
        
        // 展示Key的生成
        System.out.println("\n=== Key生成示例 ===");
        InstrumentData sample = new InstrumentData("BTC-USDT", "SPOT", "BTC", "USDT", "live");
        String key = KeyUtil.key(sample);
        System.out.println("数据: " + sample);
        System.out.println("生成的Key: " + key);
    }
}