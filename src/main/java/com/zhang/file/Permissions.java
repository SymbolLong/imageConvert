package com.zhang.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Permissions {

    public static void main(String[] args) {
        String path = "/Users/zhangsl/Downloads/permissions";
        Map<String, String> map = new HashMap<String, String>();
        map.put("create", "创建");
        map.put("edit", "编辑");
        map.put("view", "查看");
        map.put("upload", "上传图片");
        map.put("remove", "删除");
        map.put("list", "列表");
        map.put("init", "初始化");
        map.put("auditing", "审核");

        map.put("city", "城市");
        map.put("province", "省份");
        map.put("district", "地区");
        map.put("admin", "管理员");
        map.put("role", "角色");
        map.put("permission", "权限");
        map.put("user", "用户");
        map.put("agent", "代理");
        map.put("address", "地址");
        map.put("brand", "品牌");
        map.put("category", "分类");
        map.put("model", "车型");
        map.put("release", "年款");
        map.put("style", "车款");
        map.put("car", "车辆");
        map.put("order", "订单");
        map.put("book", "预约");
        map.put("bright_point", "亮点");
        map.put("color", "颜色");
        map.put("config", "配置");
        map.put("config_detail", "配置详情");
        map.put("config_type", "配置类型");
        map.put("carousel", "轮播图");
        map.put("navig_icon", "ICON");
        map.put("allocation", "调拨单");
        map.put("allocation_log", "调拨单日志");
        map.put("allocation_detail", "调拨单详情");
        map.put("check_inventory", "盘点");
        map.put("check_inventory_detail", "盘点详情");
        map.put("check_inventory_log", "盘点日志");
        map.put("inbound", "入库");
        map.put("inbound_detail", "入库详情");
        map.put("inbound_finance", "入库财务");
        map.put("inbound_log", "入库日志");
        map.put("inventory", "库存");
        map.put("outbound", "出库");
        map.put("outbound_detail", "出库详情");
        map.put("outbound_log", "出库日志");
        map.put("warehouse_area", "仓库区域");
        map.put("warehouse", "仓库");
        map.put("audit_record", "审核记录");
        map.put("order_detail", "订单详情");
        map.put("transport", "运送单");
        map.put("new", "滚动新闻");
        map.put("supplier", "供应商");
        map.put("quoted_price", "报价");
        map.put("purchase_order", "采购单");
        map.put("purchase_order_detail", "采购单详情");
        try {
            File file = new File(path);
            File sqlFile = new File(path + ".sql");
            if (sqlFile.exists()) {
                sqlFile.delete();
                sqlFile = new File(path + ".sql");
            }
            LineIterator iterator = FileUtils.lineIterator(file);
            while (iterator.hasNext()) {
                String line = iterator.nextLine();
                if (line.trim().isEmpty() || !line.contains("=")) {
                    continue;
                }
                line = line.substring(line.indexOf("=") + 3, line.length() - 2);
                String[] array = line.split("_");
                String opt = map.get(array[array.length - 1]);
                String name = map.get(line.substring(0, line.lastIndexOf("_")));
                String desc = name + opt;
                if (opt.equals("查看") || opt.equals("删除")) {
                    desc = opt + name;
                }
                String sql = "INSERT INTO `permissions` (`createDate`, `entityStatus`, `updateDate`, `description`, `name`, `permission`) VALUES ('2018-04-03 15:38:35', 'ENABLE', '2018-04-03 15:38:35', '" + desc + "', '" + desc + "', '" + line + "');\r\n";
                FileUtils.writeStringToFile(sqlFile, sql, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
