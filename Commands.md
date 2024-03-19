# Commands

List of commands provided with input and output examples.


## Table of contents
- [Commands](#commands)
  - [Table of contents](#table-of-contents)
  - [Game setup](#game-setup)
    - [Define players](#define-players)
    - [Start game](#start-game)
  - [In-game](#in-game)
    - [End turn](#end-turn)
    - [Place card](#place-card)
    - [Card attack](#card-attack)
    - [Card ability](#card-ability)
    - [Hero attack](#hero-attack)
    - [Hero ability](#hero-ability)
    - [Use spell](#use-spell)
  - [Debug](#debug)
    - [Check a player's hand](#check-a-players-hand)
    - [Check a player's deck](#check-a-players-deck)
    - [Check all cards on table](#check-all-cards-on-table)
    - [Check player turn](#check-player-turn)
    - [Check player hero](#check-player-hero)
    - [Check card on table](#check-card-on-table)
    - [Check player mana](#check-player-mana)
    - [Check spell cards in hand](#check-spell-cards-in-hand)
    - [Check frozen cards on table](#check-frozen-cards-on-table)
  - [Statistics](#statistics)
    - [Check total games played](#check-total-games-played)
    - [Check player wins](#check-player-wins)


## Game setup

### Define players

Input: define the decks for each of the two players

```json
{
  "playerOneDecks": {
    "nrCardsInDeck": 3,
    "nrDecks": 2,
    "decks": [
      [
        {
          "mana": 1,
          "attackDamage": 1,
          "health": 7,
          "description": "Standard card: A warrior who is never afraid of battle, no matter the costs",
          "colors": [
            "Red",
            "Purple"
          ],
          "name": "Goliath"
        },
        {
          "mana": 1,
          "attackDamage": 0,
          "health": 1,
          "description": "Premium card: Nobody suspects an outcast, not even the Gods.",
          "colors": [
            "Black",
            "White",
            "Red"
          ],
          "name": "The Cursed One"
        },
        {
          "mana": 1,
          "description": "Premium card: Your mind is ours. Your body is ours. Stop begging.",
          "colors": [
            "Green",
            "Black"
          ],
          "name": "Heart Hound"
        }
      ],
      [
        {
          "mana": 1,
          "description": "Standard card: The dragon was unleashed, run for your lives.",
          "colors": [
            "Red",
            "Yellow"
          ],
          "name": "Firestorm"
        },
        {
          "mana": 1,
          "attackDamage": 2,
          "health": 2,
          "description": "Premium card: Fear him. Your last breath is now.",
          "colors": [
            "Red",
            "Blue",
            "Pink"
          ],
          "name": "Berserker"
        },
        {
          "mana": 1,
          "attackDamage": 0,
          "health": 1,
          "description": "Standard card: Thrown from the highest cliff, revenge will be his glory.",
          "colors": [
            "Pink",
            "Purple"
          ],
          "name": "The Cursed One"
        }
      ]
    ]
  },
  {
    "playerTwoDecks": {
      "nrCardsInDeck": 3,
      "nrDecks": 2,
      "decks": [
        [
          {
            "mana": 1,
            "description": "Standard card: Corruption is easy, you always wanted it, now I help you embrace it.",
            "colors": [
              "Red",
              "White",
              "Grey"
            ],
            "name": "Heart Hound"
          },
          {
            "mana": 1,
            "attackDamage": 3,
            "health": 2,
            "description": "Premium card: Fear him. Your last breath is now.",
            "colors": [
              "Purple",
              "Yellow"
            ],
            "name": "Berserker"
          },
          {
            "mana": 1,
            "attackDamage": 0,
            "health": 3,
            "description": "Standard card: Thrown from the highest cliff, revenge will be his glory.",
            "colors": [
              "Black",
              "White",
              "Red"
            ],
            "name": "The Cursed One"
          }
        ],
        [
          {
            "mana": 1,
            "attackDamage": 1,
            "health": 2,
            "description": "Standard card: Trained by the best, nothing will catch him off-guard.",
            "colors": [
              "Black",
              "Grey",
              "Blue"
            ],
            "name": "Sentinel"
          },
          {
            "mana": 1,
            "description": "Standard card: The dragon was unleashed, run for your lives.",
            "colors": [
              "Red",
              "Yellow"
            ],
            "name": "Firestorm"
          },
          {
            "mana": 1,
            "attackDamage": 6,
            "health": 4,
            "description": "Standard card: Death incarnate. No better time to die than now.",
            "colors": [
              "Orange",
              "Red",
              "Black"
            ],
            "name": "The Ripper"
          }
        ]
      ]
    }
  }
}
```


### Start game

Input: decks chosen by players, seed for deck shuffling, heroes for each player and starting player

```json
{
  "startGame": {
    "playerOneDeckIdx": 1,
    "playerTwoDeckIdx": 0,
    "shuffleSeed": 882661,
    "playerOneHero": {
      "mana": 2,
      "description": "Standard card: Absolute order, a soldier must devote his life to his General.",
      "colors": [
        "Red",
        "Black",
        "Purple"
      ],
      "name": "General Kocioraw"
    },
    "playerTwoHero": {
      "mana": 2,
      "description": "Premium card: Glory to all we see, glory to all he is, glory to everyone who will try to escape him.",
      "colors": [
        "Black",
        "Red",
        "Grey"
      ],
      "name": "Lord Royce"
    },
    "startingPlayer": 1
  }
}
```


## In-game

### End turn

Input

```json
{
  "command": "endPlayerTurn"
}
```


### Place card

Input: index of card in hand

```json
{
  "command": "placeCard",
  "handIdx": 1
}
```
Possible errors:
  * not enough mana
  * table already full
  * trying to place spell card
```json
{
  "command": "placeCard",
  "handIdx": 0,
  "error": "Cannot place environment card on table."
}
```


### Card attack

Input: coordinates of the card which attacks and the one which is attacked

```json
{
  "command": "cardUsesAttack",
  "cardAttacker": {
    "x": 1,
    "y": 0
  },
  "cardAttacked": {
    "x": 3,
    "y": 0
  }
}
```

Possible errors:
  * attacked card is not an enemy
  * card has already attacked
  * card is frozen
  * if enemy has `Tank` card, attacked card is not a `Tank` one

```json
{
  "command": "cardUsesAttack",
  "cardAttacker": {
    "x": 3,
    "y": 1
  },
  "cardAttacked": {
    "x": 3,
    "y": 1
  },
  "error": "Attacked card does not belong to the enemy."
}
```


### Card ability

Input: coordinates of the card which attacks and the one which is attacked

```json
{
  "command": "cardUsesAbility",
  "cardAttacker": {
    "x": 2,
    "y": 0
  },
  "cardAttacked": {
    "x": 1,
    "y": 0
  }
}
```

Possible errors:
  * card has already attacked
  * card is frozen
  * other errors depending on card type

```json
{
  "command": "cardUsesAbility",
  "cardAttacker": {
    "x": 3,
    "y": 0
  },
  "cardAttacked": {
    "x": 3,
    "y": 0
  },
  "error": "Attacker card is frozen."
}
```


### Hero attack

Input: coordinates of attacker card

```json
{
  "command": "useAttackHero",
  "cardAttacker": {
    "x": 3,
    "y": 0
  }
}
```

Possible errors:
  * card has already attacked
  * card is frozen
  * if enemy has `Tank` card, attacked card is not a `Tank` one

```json
{
  "command": "useAttackHero",
  "cardAttacker": {
    "x": 2,
    "y": 0
  },
  "error": "Attacked card is not of type 'Tank'."
}
```

Output: win message if the hero has been killed

```json
{
  "gameEnded": "Player one killed the enemy hero."
}
```


### Hero ability

Input: row affected by a hero's ability

```json
{
  "command": "useHeroAbility",
  "affectedRow": 3
}
```

Possible errors:
  * not enough mana
  * hero already attacked
  * other errors depending on hero type

```json
{
  "command": "useHeroAbility",
  "affectedRow": 3,
  "error": "Selected row does not belong to the enemy."
}
```


### Use spell

Input: index of card in hand and affected row

```json
{
  "command": "useEnvironmentCard",
  "handIdx": 0,
  "affectedRow": 0
}
```

Possible errors:
  * chosen card is not a spell
  * not enough mana
  * chosen row is not an enemy row
  * other errors depending on spell type

```json
{
  "command": "useEnvironmentCard",
  "handIdx": 1,
  "affectedRow": 2,
  "error": "Chosen row does not belong to the enemy."
}
```


## Debug

### Check a player's hand

Input: player index

```json
{
  "command": "getCardsInHand",
  "playerIdx": 2
}
```

Output: array of cards in player's hand

```json
{
  "command": "getCardsInHand",
  "playerIdx": 2,
  "output": [
    {
      "mana": 4,
      "attackDamage": 2,
      "health": 7,
      "description": "Premium card: No matter how many, his body will fight. Save your breath for the Mountain is strong.",
      "colors": [
        "Red",
        "Purple"
      ],
      "name": "Goliath"
    },
    {
      "mana": 4,
      "attackDamage": 4,
      "health": 4,
      "description": "Standard card: When he is near, close your eyes. They betray your mind.",
      "colors": [
        "Red",
        "Black"
      ],
      "name": "Miraj"
    }
  ]
}
```


### Check a player's deck

Input: player index

```json
{
  "command": "getPlayerDeck",
  "playerIdx": 2
}
```

Output: array of cards in player's deck

```json
{
  "command": "getPlayerDeck",
  "playerIdx": 2,
  "output": [
    {
      "mana": 3,
      "attackDamage": 3,
      "health": 5,
      "description": "Standard card: He has seen everything, his mind has never been asleep since the making of the realm.",
      "colors": [
        "Brown",
        "Blue"
      ],
      "name": "Warden"
    }
  ]
}
```

### Check all cards on table

Input

```json
{
  "command": "getCardsOnTable"
}
```

Output: array of cards on table

```json
{
  "command": "getCardsOnTable",
  "output": [
    {
      "mana": 3,
      "attackDamage": 6,
      "health": 3,
      "description": "Premium card: Ravished by the winds of hell, no soul is to be forgiven.",
      "colors": [
        "Orange",
        "Red",
        "Black"
      ],
      "name": "The Ripper"
    },
    {
      "mana": 5,
      "attackDamage": 4,
      "health": 3,
      "description": "Standard card: When he is near, close your eyes. They betray your mind.",
      "colors": [
        "Yellow",
        "Orange",
        "Red"
      ],
      "name": "Miraj"
    },
    {
      "mana": 4,
      "attackDamage": 6,
      "health": 3,
      "description": "Standard card: Death incarnate. No better time to die than now.",
      "colors": [
        "Orange",
        "Red",
        "Black"
      ],
      "name": "The Ripper"
    },
    {
      "mana": 3,
      "attackDamage": 1,
      "health": 8,
      "description": "Standard card: A warrior who is never afraid of battle, no matter the costs",
      "colors": [
        "Brown",
        "Black",
        "Grey"
      ],
      "name": "Goliath"
    },
    {
      "mana": 3,
      "attackDamage": 5,
      "health": 3,
      "description": "Premium card: Ravished by the winds of hell, no soul is to be forgiven.",
      "colors": [
        "Black",
        "Yellow"
      ],
      "name": "The Ripper"
    },
    {
      "mana": 3,
      "attackDamage": 2,
      "health": 5,
      "description": "Standard card: He has seen everything, his mind has never been asleep since the making of the realm.",
      "colors": [
        "Black",
        "White",
        "Green"
      ],
      "name": "Warden"
    },
    {
      "mana": 4,
      "attackDamage": 4,
      "health": 4,
      "description": "Standard card: When he is near, close your eyes. They betray your mind.",
      "colors": [
        "Red",
        "Black"
      ],
      "name": "Miraj"
    },
    {
      "mana": 2,
      "attackDamage": 3,
      "health": 6,
      "description": "Standard card: He has seen everything, his mind has never been asleep since the making of the realm.",
      "colors": [
        "Brown",
        "Blue"
      ],
      "name": "Warden"
    },
    {
      "mana": 4,
      "attackDamage": 2,
      "health": 7,
      "description": "Premium card: No matter how many, his body will fight. Save your breath for the Mountain is strong.",
      "colors": [
        "Red",
        "Purple"
      ],
      "name": "Goliath"
    },
    {
      "mana": 4,
      "attackDamage": 1,
      "health": 7,
      "description": "Premium card: No matter how many, his body will fight. Save your breath for the Mountain is strong.",
      "colors": [
        "Red",
        "Purple"
      ],
      "name": "Goliath"
    }
  ]
}
```


### Check player turn

Input

```json
{
  "command": "getPlayerTurn"
}
```

Output: index of player

```json
{
  "command": "getPlayerTurn",
  "output": 2
}
```


### Check player hero

Input: player index

```json
{
  "command": "getPlayerHero",
  "playerIdx": 1
}
```

Output: hero card of player

```json
{
  "command": "getPlayerHero",
  "playerIdx": 1,
  "output": {
    "mana": 2,
    "description": "Premium card: An army is not sufficient, he is ready obliterate, it is time for chaos.",
    "colors": [
      "Red",
      "Black",
      "Purple"
    ],
    "name": "General Kocioraw",
    "health": 30
  }
}
```


### Check card on table

Input: coordinates on the table

```json
{
  "command": "getCardAtPosition",
  "x": 3,
  "y": 0
}
```

Output: card info

```json
{
  "command": "getCardAtPosition",
  "x": 3,
  "y": 0,
  "output": {
    "mana": 2,
    "attackDamage": 0,
    "health": 3,
    "description": "Premium card: Nobody suspects an outcast, not even the Gods.",
    "colors": [
      "Black",
      "White",
      "Red"
    ],
    "name": "The Cursed One"
  }
}
```

Possible errors:
  * no card at given position

```json
{
  "command": "getCardAtPosition",
  "x": 3,
  "y": 0,
  "output": "No card available at that position."
}
```


### Check player mana

Input: player index

```json
{
  "command": "getPlayerMana",
  "playerIdx": 2
}
```

Ouput: player mana

```json
{
  "command": "getPlayerMana",
  "playerIdx": 2,
  "output": 3
}
```

### Check spell cards in hand

Input: player index

```json
{
  "command": "getEnvironmentCardsInHand",
  "playerIdx": 1
}
```

Output: array of spell cards in hand

```json
{
  "command": "getEnvironmentCardsInHand",
  "playerIdx": 1,
  "output": [
    {
      "mana": 2,
      "description": "Premium card: Your mind is ours. Your body is ours. Stop begging.",
      "colors": [
        "Red",
        "White",
        "Grey"
      ],
      "name": "Heart Hound"
    },
    {
      "mana": 2,
      "description": "Premium card: Your mind is ours. Your body is ours. Stop begging.",
      "colors": [
        "Green",
        "Black"
      ],
      "name": "Heart Hound"
    }
  ]
}
```


### Check frozen cards on table

Input

```json
{
  "command": "getFrozenCardsOnTable"
}
```

Output: array of cards on table

```json
{
  "command": "getFrozenCardsOnTable",
  "output": [
    {
      "mana": 1,
      "attackDamage": 6,
      "health": 4,
      "description": "Premium card: Ravished by the winds of hell, no soul is to be forgiven.",
      "colors": [
        "Orange",
        "Red",
        "Black"
      ],
      "name": "The Ripper"
    }
  ]
}
```

## Statistics

### Check total games played

Input

```json
{
  "command": "getTotalGamesPlayed"
}
```

Output: number of games played

```json
{
  "command": "getTotalGamesPlayed",
  "output": 2
}
```

### Check player wins

Input

```json
{
  "command": "getPlayerOneWins"
}
```

Output: number of wins

```json
{
  "command": "getPlayerTwoWins",
  "output": 2
}
```

