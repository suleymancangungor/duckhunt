public class Gun {
    int capacity = 3;
    int bulletCount = 3;

    public Gun(){
        //System.out.println("Bullet Count : " + bulletCount);
    }

    public void Shoot(){
        if (bulletCount != 0) {
            bulletCount--;
            //System.out.println("Bullet Left : " + bulletCount);
        } else {
            //System.out.println("You need to reload! Press R key");
        }
    }

    public void Reload(){
        if (bulletCount < capacity) {
            bulletCount++;
            //System.out.println("Bullet Count : " + bulletCount);
        } else {
            //System.out.println("Your magazine is full!");
        }
    }

    public int getBulletCount(){
        return bulletCount;
    }

    public void setCapacity(int newCapacity){
        capacity = newCapacity;
    }

    public void setBulletCount(int newBulletCount){
        bulletCount = newBulletCount;
    }
}
