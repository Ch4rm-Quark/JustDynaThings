---
navigation:
  title: Eclipse Alloy Anvil
  icon: "justdynathings:eclipse_alloy_anvil"
  position: 4
  parent: justdynathings:anvils.md
item_ids:
  - justdynathings:eclipse_alloy_anvil
---

# Eclipse Alloy Anvil

An anvil that repair items using Forge Energy (FE) like <ItemLink id="justdynathings:celestigem_anvil"/> and a liquid coolant.
When [coolant](https://github.com/DevDyna/JustDynaThings/blob/main/src/generated/resources/data/justdynathings/data_maps/fluid/anvils/eclipsealloy_repair.json) is added, it will repair the item based on the formula: `coolant-efficiency / 100 x total-damage`
1000 durability will be restored per tick until the item is fully repaired.

<BlockImage id="justdynathings:eclipse_alloy_anvil" scale="4.0"/>

<RecipeFor id="justdynathings:eclipse_alloy_anvil" />
