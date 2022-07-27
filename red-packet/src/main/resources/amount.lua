local totalAcount = tonumber(redis.call('hget', 'redPacket', 'totalAcount'))
return totalAcount