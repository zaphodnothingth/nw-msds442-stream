import numpy as np
import random
import math

def gen_dt(start=np.datetime64('2021-01-01 00:00:00.000000').astype('uint64'),
    end=np.datetime64(datetime.now()).astype('uint64')
    ):
    return random.randint(start,end)
    
    
def gen_uuid():
    return uuid.uuid1().hex


cols = ['trans_id','trans_datetime','product_id','user_id','store_id','trans_long','trans_lat']
trans_id = [gen_uuid() for i in range(100)]
trans_datetime = [gen_dt() for i in range(100)]
product_id = [gen_uuid() for i in range(100)]
user_id = [gen_uuid() for i in range(100)]
store_id = [gen_uuid() for i in range(100)]
lat = np.random.uniform(-90,90,100)
long = np.random.uniform(-180,180,100)


arr = np.array([trans_id,
    trans_datetime,
    product_id,
    user_id,
    store_id,
    lat,
    long])


with open('Project_Phase_1_Stevens-mock_data.csv','a') as f:
    f.write(str(cols) + '\n')
    np.savetxt(f,arr.T, delimiter=",", fmt="%s")

