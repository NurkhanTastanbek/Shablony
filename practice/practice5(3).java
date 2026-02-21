import java.util.ArrayList;
import java.util.List;

interface ICloneable<T> {
    T clone();
}

class Weapon implements ICloneable<Weapon> {
    public String name;
    public int damage;

    public Weapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    @Override
    public Weapon clone() {
        return new Weapon(this.name, this.damage);
    }

    @Override
    public String toString() {
        return name + " (Damage: " + damage + ")";
    }
}

class Armor implements ICloneable<Armor> {
    public String name;
    public int defense;

    public Armor(String name, int defense) {
        this.name = name;
        this.defense = defense;
    }

    @Override
    public Armor clone() {
        return new Armor(this.name, this.defense);
    }

    @Override
    public String toString() {
        return name + " (Defense: " + defense + ")";
    }
}

class Skill implements ICloneable<Skill> {
    public String name;
    public String type;

    public Skill(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public Skill clone() {
        return new Skill(this.name, this.type);
    }

    @Override
    public String toString() {
        return name + " [" + type + "]";
    }
}

class Character implements ICloneable<Character> {
    public String name;
    public int health;
    public int strength;
    public int agility;
    public int intelligence;
    
    public Weapon weapon;
    public Armor armor;
    public List<Skill> skills = new ArrayList<>();

    public Character(String name, int h, int s, int a, int i) {
        this.name = name;
        this.health = h;
        this.strength = s;
        this.agility = a;
        this.intelligence = i;
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    @Override
    public Character clone() {
        Character copy = new Character(this.name, this.health, this.strength, this.agility, this.intelligence);
        if (this.weapon != null) copy.weapon = this.weapon.clone();
        if (this.armor != null) copy.armor = this.armor.clone();
        for (Skill s : this.skills) {
            copy.addSkill(s.clone());
        }
        return copy;
    }

    @Override
    public String toString() {
        return "Character: " + name + "\n" +
               "Stats: HP:" + health + " STR:" + strength + " AGI:" + agility + " INT:" + intelligence + "\n" +
               "Equipment: " + weapon + ", " + armor + "\n" +
               "Skills: " + skills + "\n";
    }
}

public class Main {
    public static void main(String[] args) {
        Character warriorTemplate = new Character("Warrior Template", 100, 15, 10, 5);
        warriorTemplate.weapon = new Weapon("Iron Sword", 12);
        warriorTemplate.armor = new Armor("Steel Plate", 20);
        warriorTemplate.addSkill(new Skill("Slash", "Physical"));
        warriorTemplate.addSkill(new Skill("Block", "Physical"));

        System.out.println("--- Template Character ---");
        System.out.println(warriorTemplate);

        Character player1 = warriorTemplate.clone();
        player1.name = "Aragon";
        player1.weapon.name = "Excalibur";
        player1.weapon.damage = 50;
        player1.addSkill(new Skill("Holy Strike", "Magic"));

        Character player2 = warriorTemplate.clone();
        player2.name = "Orc Brute";
        player2.strength = 30;
        player2.weapon = new Weapon("Heavy Axe", 25);
        player2.skills.remove(0);

        System.out.println("--- Player 1 (Modified Clone) ---");
        System.out.println(player1);

        System.out.println("--- Player 2 (Modified Clone) ---");
        System.out.println(player2);

        System.out.println("--- Template Remains Unchanged ---");
        System.out.println(warriorTemplate);
    }
}
