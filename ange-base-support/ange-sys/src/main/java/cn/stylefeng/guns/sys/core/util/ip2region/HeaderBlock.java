package cn.stylefeng.guns.sys.core.util.ip2region;

/**
 * header block class
 * 
 * @author chenxin<chenxin619315@gmail.com>
*/
public class HeaderBlock 
{
    /**
     * index block start ip address
    */
    private long indexStartIp;
    
    /**
     * ip address 
    */
    private int indexPtr;
    
    public HeaderBlock( long indexStartIp, int indexPtr )
    {
        this.indexStartIp = indexStartIp;
        this.indexPtr = indexPtr;
    }

    public long getIndexStartIp()
    {
        return indexStartIp;
    }

    public HeaderBlock setIndexStartIp(long indexStartIp)
    {
        this.indexStartIp = indexStartIp;
        return this;
    }

    public int getIndexPtr()
    {
        return indexPtr;
    }

    public HeaderBlock setIndexPtr(int indexPtr)
    {
        this.indexPtr = indexPtr;
        return this;
    }
    
    /**
     * get the bytes for db storage
     * 
     * @return    byte[]
    */
    public byte[] getBytes()
    {
        /*
         * +------------+-----------+
         * | 4bytes        | 4bytes    |
         * +------------+-----------+
         *  start ip      index ptr
        */
        byte[] b = new byte[8];
        
        IpToRegionUtil.writeIntLong(b, 0, indexStartIp);
        IpToRegionUtil.writeIntLong(b, 4, indexPtr);
        
        return b;
    }
}
