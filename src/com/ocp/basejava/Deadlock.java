package com.ocp.basejava;

import java.util.Random;

public class Deadlock {
    static Prey antelope = new Prey("Антилопа", 500);
    static Prey zebra = new Prey("Зебра", 1000);
    static Predator tiger = new Predator("Тигр");
    static Predator leopard = new Predator("Леопард");

    public static void main(String[] args) {
        new Thread(() -> bigHunt(tiger, antelope, zebra)).start();
        new Thread(() -> bigHunt(leopard, zebra, antelope)).start();
    }

    private static void bigHunt(Predator pr, Prey p1, Prey p2) {
        synchronized (p1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (p2) {
                pr.hunt(p1);
                pr.hunt(p2);
            }
        }
    }



    public static class Predator  {
        private String name;
        final Random random = new Random();

        public Predator(String name) {
            this.name = name;
        }

        public void hunt(Prey prey) {
            int damage = random.nextInt(101);
            prey.getDamage(damage);
            System.out.println(name + " охотится на " + prey);
            System.out.println(name+ " нанес урон "+prey+": " + damage);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class Prey {
        private String name;
        private int health;

        public Prey(String name, int health) {
            this.name = name;
            this.health = health;
        }

        public void getDamage(int value) {
            health -= value;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}

