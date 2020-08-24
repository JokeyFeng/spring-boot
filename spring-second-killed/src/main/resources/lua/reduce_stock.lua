local sku = KEYS[1] --目标sku
local num = tonumber(ARGV[1]) --需要扣减的数量
local stock = tonumber(redis.call('GET',SKU)) --获取目标sku的库存数量
local result = 0
if (stock >= num)
then
    redis.call('DECRBY',sku,num) --原子性扣减库存
    result = 1
end
return result