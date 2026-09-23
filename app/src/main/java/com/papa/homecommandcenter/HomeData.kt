package com.papa.homecommandcenter

object HomeData {
    private fun t(
        id: String,
        room: String,
        title: String,
        type: TaskType,
        trade: Trade = Trade.GENERAL,
        effort: Effort = Effort.QUICK,
        dependencies: Set<String> = emptySet(),
        note: String? = null
    ) = HomeTask(id, room, title, type, trade, effort, dependencies, note)

    val tasks: List<HomeTask> = listOf(
        // Kitchen
        t("kitchen-pulls", "Kitchen", "Install pulls on cabinets", TaskType.DIY, effort = Effort.SESSION),

        // Long hallway
        t("long-keenan", "Long Hallway", "Purchase Crate & Barrel Keenan 50", TaskType.PURCHASE),
        t("long-rugs", "Long Hallway", "Purchase two rugs — 3×10 and 3×12", TaskType.PURCHASE),
        t("long-picture-light", "Long Hallway", "Purchase battery-operated picture light", TaskType.PURCHASE),
        t("long-bench", "Long Hallway", "Purchase hallway bench", TaskType.PURCHASE),
        t(
            "long-art-display",
            "Long Hallway",
            "Put up ballerina painting above bench and install picture light",
            TaskType.DIY,
            trade = Trade.MOUNTING,
            effort = Effort.SESSION,
            dependencies = setOf("long-picture-light", "long-bench")
        ),
        t(
            "long-mirror",
            "Long Hallway",
            "Put up 5 ft black round mirror",
            TaskType.CONTRACTOR,
            trade = Trade.MOUNTING,
            effort = Effort.PRO
        ),

        // Short hallway
        t("short-rug", "Short Hallway", "Purchase rug for short hallway", TaskType.PURCHASE),
        t(
            "short-art",
            "Short Hallway",
            "Put up HomeGoods painting — “hands full / hearts full”",
            TaskType.DIY,
            trade = Trade.MOUNTING
        ),

        // Guest bath
        t(
            "guest-ceiling",
            "Guest Bath",
            "Paint ceiling Benjamin Moore White Dove",
            TaskType.DIY,
            trade = Trade.PAINTING,
            effort = Effort.SESSION
        ),
        t(
            "guest-wall-color",
            "Guest Bath",
            "Choose color for single wall / towel-rack wall",
            TaskType.DECISION
        ),
        t("guest-wood", "Guest Bath", "Purchase wood for feature wall", TaskType.PURCHASE),
        t(
            "guest-feature-wall",
            "Guest Bath",
            "Install feature wall",
            TaskType.DIY,
            trade = Trade.CARPENTRY,
            effort = Effort.WEEKEND,
            dependencies = setOf("guest-wood")
        ),
        t("guest-pulls", "Guest Bath", "Install pulls on cabinets", TaskType.DIY, effort = Effort.SESSION),
        t(
            "guest-sand-dollars",
            "Guest Bath",
            "Put up sand dollars above toilet",
            TaskType.DIY,
            trade = Trade.MOUNTING
        ),

        // Laundry
        t("laundry-cabinets-buy", "Laundry Room", "Purchase laundry cabinets", TaskType.PURCHASE),
        t(
            "laundry-cabinets-install",
            "Laundry Room",
            "Install laundry cabinets",
            TaskType.CONTRACTOR,
            trade = Trade.CARPENTRY,
            effort = Effort.PRO,
            dependencies = setOf("laundry-cabinets-buy")
        ),
        t(
            "laundry-pulls",
            "Laundry Room",
            "Install gold pulls on cabinet doors",
            TaskType.DIY,
            effort = Effort.SESSION,
            dependencies = setOf("laundry-cabinets-install")
        ),
        t(
            "laundry-wainscoting",
            "Laundry Room",
            "Install wainscoting",
            TaskType.DIY,
            trade = Trade.CARPENTRY,
            effort = Effort.WEEKEND
        ),
        t(
            "laundry-wainscoting-paint",
            "Laundry Room",
            "Paint wainscoting BM White Dove 100% in Scuff-X",
            TaskType.DIY,
            trade = Trade.PAINTING,
            effort = Effort.SESSION,
            dependencies = setOf("laundry-wainscoting")
        ),
        t(
            "laundry-backsplash",
            "Laundry Room",
            "Install backsplash above sink and across washer/dryer",
            TaskType.CONTRACTOR,
            trade = Trade.CARPENTRY,
            effort = Effort.PRO,
            dependencies = setOf("laundry-cabinets-install")
        ),
        t(
            "laundry-faucet-buy",
            "Laundry Room",
            "Purchase Delta Trask or Delta Essa faucet — Trask preferred",
            TaskType.PURCHASE
        ),
        t(
            "laundry-faucet-change",
            "Laundry Room",
            "Change faucet",
            TaskType.DIY,
            trade = Trade.PLUMBING,
            effort = Effort.SESSION,
            dependencies = setOf("laundry-faucet-buy")
        ),
        t("laundry-wallpaper-buy", "Laundry Room", "Purchase Amity Floral wallpaper", TaskType.PURCHASE),
        t(
            "laundry-wallpaper",
            "Laundry Room",
            "Wallpaper entire laundry room",
            TaskType.CONTRACTOR,
            trade = Trade.WALLPAPER,
            effort = Effort.PRO,
            dependencies = setOf("laundry-wallpaper-buy", "laundry-backsplash")
        ),
        t(
            "laundry-mirror",
            "Laundry Room",
            "Put up heart-shaped wall mirror above sink",
            TaskType.DIY,
            trade = Trade.MOUNTING,
            dependencies = setOf("laundry-wallpaper")
        ),
        t("laundry-rack", "Laundry Room", "Purchase hanging rack for clothing", TaskType.PURCHASE),
        t("laundry-runner", "Laundry Room", "Purchase runner rug", TaskType.PURCHASE),

        // Master bedroom
        t(
            "mbed-repaint",
            "Master Bedroom",
            "Repaint solid Pale Oak 100% flat",
            TaskType.DIY,
            trade = Trade.PAINTING,
            effort = Effort.WEEKEND
        ),
        t(
            "mbed-glaze",
            "Master Bedroom",
            "Redo decorative layer 3:1 glaze — 75/50% mix, mostly 75%",
            TaskType.DIY,
            trade = Trade.PAINTING,
            effort = Effort.WEEKEND,
            dependencies = setOf("mbed-repaint")
        ),
        t(
            "mbed-rug",
            "Master Bedroom",
            "Install 8×10 Arrows rug",
            TaskType.DIY,
            effort = Effort.SESSION,
            dependencies = setOf("mbed-glaze")
        ),
        t(
            "mbed-circles",
            "Master Bedroom",
            "Install Bouclair Mango Wood Circles above bed",
            TaskType.DIY,
            trade = Trade.MOUNTING,
            dependencies = setOf("mbed-glaze")
        ),
        t(
            "mbed-bed-decision",
            "Master Bedroom",
            "Confirm whether Martha Stewart Maisie is the new bed",
            TaskType.DECISION
        ),
        t(
            "mbed-bed-buy",
            "Master Bedroom",
            "Purchase new bed",
            TaskType.PURCHASE,
            dependencies = setOf("mbed-bed-decision")
        ),
        t(
            "mbed-mattress",
            "Master Bedroom",
            "Move mattress from guest room into master bedroom",
            TaskType.DIY,
            effort = Effort.SESSION,
            dependencies = setOf("mbed-bed-buy", "mbed-rug")
        ),
        t(
            "mbed-bench",
            "Master Bedroom",
            "Put bench in front of bed",
            TaskType.DIY,
            dependencies = setOf("mbed-mattress")
        ),
        t(
            "mbed-dresser",
            "Master Bedroom",
            "Move dresser",
            TaskType.DIY,
            effort = Effort.SESSION,
            dependencies = setOf("mbed-glaze")
        ),
        t(
            "mbed-mirror",
            "Master Bedroom",
            "Install mirror above dresser",
            TaskType.DIY,
            trade = Trade.MOUNTING,
            dependencies = setOf("mbed-dresser")
        ),
        t(
            "mbed-vase",
            "Master Bedroom",
            "Place Renzo floor vase beside dresser at window; purchase florals",
            TaskType.DIY
        ),
        t(
            "mbed-shades-measure",
            "Master Bedroom",
            "Measure windows for bamboo shades",
            TaskType.MEASURE
        ),
        t("mbed-end-tables", "Master Bedroom", "Purchase two end tables from Etsy", TaskType.PURCHASE),
        t(
            "mbed-lamps",
            "Master Bedroom",
            "Purchase two bedside lamps; may require custom appointment",
            TaskType.PURCHASE
        ),

        // Master bathroom - toilet
        t(
            "mbath-toilet-paint",
            "Master Bathroom — Toilet",
            "Purchase HGTV Soft Putty 100% bathroom paint from Lowe’s",
            TaskType.PURCHASE
        ),
        t(
            "mbath-toilet-art",
            "Master Bathroom — Toilet",
            "Put 3-piece leaf painting canvas above toilet",
            TaskType.DIY,
            trade = Trade.MOUNTING
        ),
        t(
            "mbath-toilet-tp",
            "Master Bathroom — Toilet",
            "Put Brockton Bamboo TP rack beside toilet",
            TaskType.DIY
        ),

        // Master bathroom - main
        t(
            "mbath-electrical",
            "Master Bathroom — Main",
            "Hardwire mirrors and install electrical box for pot light above bathtub",
            TaskType.CONTRACTOR,
            trade = Trade.ELECTRICAL,
            effort = Effort.PRO
        ),
        t(
            "mbath-paint",
            "Master Bathroom — Main",
            "Purchase HGTV Soft Putty 100% bathroom paint from Lowe’s",
            TaskType.PURCHASE
        ),
        t(
            "mbath-shelves",
            "Master Bathroom — Main",
            "Install two Kate & Laurel mango wood shelves above towel rack",
            TaskType.DIY,
            trade = Trade.MOUNTING
        ),
        t(
            "mbath-shower-head",
            "Master Bathroom — Main",
            "Install new shower head",
            TaskType.DIY,
            trade = Trade.PLUMBING,
            effort = Effort.SESSION
        ),
        t(
            "mbath-dispenser",
            "Master Bathroom — Main",
            "Install new soap/shampoo dispenser in shower",
            TaskType.DIY
        ),
        t(
            "mbath-dubai-buy",
            "Master Bathroom — Main",
            "Purchase Roselli Trading Company Dubai canisters ×2, toothbrush holder and soap dish",
            TaskType.PURCHASE
        ),
        t(
            "mbath-dubai-place",
            "Master Bathroom — Main",
            "Put Dubai set on counter",
            TaskType.DIY,
            dependencies = setOf("mbath-dubai-buy")
        ),
        t(
            "mbath-drawers",
            "Master Bathroom — Main",
            "Clean drawers and install liners",
            TaskType.DIY,
            effort = Effort.SESSION
        ),
        t(
            "mbath-circles",
            "Master Bathroom — Main",
            "Install Bouclair Mango Wood Circles on wall shared with toilet",
            TaskType.DIY,
            trade = Trade.MOUNTING
        ),
        t("mbath-stool", "Master Bathroom — Main", "Put Paulownia stool beside tub", TaskType.DIY),
        t("mbath-pulls", "Master Bathroom — Main", "Install pulls on cabinets", TaskType.DIY, effort = Effort.SESSION),

        // Main living room
        t(
            "living-tv-decision",
            "Main Living Room",
            "Choose TCL TV — QM7L Pro from Costco or QM8L",
            TaskType.DECISION
        ),
        t(
            "living-tv-buy",
            "Main Living Room",
            "Purchase selected TCL TV",
            TaskType.PURCHASE,
            dependencies = setOf("living-tv-decision")
        ),
        t("living-shelf-buy", "Main Living Room", "Purchase shelving for sound bar below TV", TaskType.PURCHASE),
        t(
            "living-tv-mount",
            "Main Living Room",
            "Mount TV above fireplace",
            TaskType.CONTRACTOR,
            trade = Trade.MOUNTING,
            effort = Effort.PRO,
            dependencies = setOf("living-tv-buy")
        ),
        t(
            "living-shelf-install",
            "Main Living Room",
            "Install shelving under TV above fireplace",
            TaskType.CONTRACTOR,
            trade = Trade.MOUNTING,
            effort = Effort.PRO,
            dependencies = setOf("living-shelf-buy")
        ),
        t("living-curtain-rods", "Main Living Room", "Purchase curtain rods for outer curtains", TaskType.PURCHASE),
        t("living-tree", "Main Living Room", "Put up Christmas tree", TaskType.DIY, effort = Effort.SESSION),
        t(
            "living-arch-mirror",
            "Main Living Room",
            "Move long arch mirror to right of fireplace",
            TaskType.DIY,
            effort = Effort.SESSION
        ),
        t(
            "living-sectional",
            "Main Living Room",
            "Purchase sectional couch in denim or dark blue",
            TaskType.PURCHASE
        ),
        t("living-drum-table", "Main Living Room", "Purchase drum table", TaskType.PURCHASE),

        // Garage
        t(
            "garage-electrical",
            "Garage",
            "Hardwire second garage-door opener beside first and move outlet on long wall",
            TaskType.CONTRACTOR,
            trade = Trade.ELECTRICAL,
            effort = Effort.PRO
        ),
        t(
            "garage-tv",
            "Garage",
            "Mount TV on wall",
            TaskType.DIY,
            trade = Trade.MOUNTING,
            effort = Effort.SESSION
        ),
        t(
            "garage-boxes",
            "Garage",
            "Move boxes to guest room",
            TaskType.DIY,
            effort = Effort.SESSION,
            dependencies = setOf("mbed-mattress"),
            note = "Guest room is currently being used for sleeping, so this waits until the mattress moves."
        ),
        t(
            "garage-floor",
            "Garage",
            "Clean floor and install gym flooring",
            TaskType.DIY,
            trade = Trade.FLOORING,
            effort = Effort.WEEKEND,
            dependencies = setOf("garage-boxes")
        ),
        t(
            "garage-mirrors",
            "Garage",
            "Install mirrors along long wall of gym",
            TaskType.DIY,
            trade = Trade.MOUNTING,
            effort = Effort.WEEKEND,
            dependencies = setOf("garage-floor")
        ),
        t(
            "garage-cabinet-color",
            "Garage",
            "Choose IKEA cabinet color — olive green or burgundy/violet",
            TaskType.DECISION
        ),
        t(
            "garage-cabinet-measure",
            "Garage",
            "Measure open space and consult for IKEA cabinet installation",
            TaskType.MEASURE,
            dependencies = setOf("garage-cabinet-color")
        ),
        t(
            "garage-opener-exterior",
            "Garage",
            "Install garage-door opener control for single door on house exterior",
            TaskType.DIY,
            effort = Effort.SESSION
        ),
        t("garage-softener", "Garage", "Purchase water softener", TaskType.PURCHASE),
        t(
            "garage-workbench",
            "Garage",
            "Purchase tool storage and workbench for Chris — Husky?",
            TaskType.PURCHASE
        ),

        // Office
        t(
            "office-floor",
            "Office",
            "Clean floors and install 8×10 rug",
            TaskType.DIY,
            trade = Trade.FLOORING,
            effort = Effort.SESSION
        ),
        t("office-futon", "Office", "Move futon to office on wall shared with Che Che’s room", TaskType.DIY, effort = Effort.SESSION),
        t(
            "office-bookshelf",
            "Office",
            "Put together piano bookshelf and add books/trinkets",
            TaskType.DIY,
            effort = Effort.SESSION
        ),
        t(
            "office-curtain-rod",
            "Office",
            "Put up Umbra Ringlet curtain rod",
            TaskType.DIY,
            trade = Trade.MOUNTING,
            effort = Effort.SESSION
        ),
        t(
            "office-curtains",
            "Office",
            "Purchase Twopages blackout curtains in Beige White",
            TaskType.PURCHASE
        ),
        t("office-concord", "Office", "Purchase Concord panels", TaskType.PURCHASE),
        t(
            "office-switch",
            "Office",
            "Move light switch to doorway wall",
            TaskType.CONTRACTOR,
            trade = Trade.ELECTRICAL,
            effort = Effort.PRO
        ),
        t(
            "office-desk",
            "Office",
            "Move computer desk to former light-switch wall",
            TaskType.DIY,
            effort = Effort.SESSION,
            dependencies = setOf("office-switch")
        ),
        t(
            "office-tv",
            "Office",
            "Install TV above computers",
            TaskType.DIY,
            trade = Trade.MOUNTING,
            effort = Effort.SESSION
        ),
        t(
            "office-shelves",
            "Office",
            "Install four shelves for feature wall",
            TaskType.DIY,
            trade = Trade.MOUNTING,
            effort = Effort.SESSION
        ),
        t("office-doors-remove", "Office", "Remove existing doors from hinges", TaskType.DIY, effort = Effort.SESSION),
        t(
            "office-barn-door-buy",
            "Office",
            "Purchase double barn door with slats and frosted windows",
            TaskType.PURCHASE
        ),
        t(
            "office-barn-door-install",
            "Office",
            "Install double barn door on office exterior",
            TaskType.DIY,
            trade = Trade.CARPENTRY,
            effort = Effort.WEEKEND,
            dependencies = setOf("office-doors-remove", "office-barn-door-buy")
        ),

        // Outside
        t(
            "outside-termite",
            "Outside Exterior",
            "Make appointment to spray exterior property for termites — not TAEXX system",
            TaskType.APPOINTMENT
        ),
        t(
            "outside-gutters",
            "Outside Exterior",
            "Install rain gutters",
            TaskType.CONTRACTOR,
            trade = Trade.GUTTERS,
            effort = Effort.PRO
        ),
        t(
            "outside-ring",
            "Outside Exterior",
            "Install Ring cameras",
            TaskType.DIY,
            trade = Trade.SECURITY,
            effort = Effort.SESSION
        ),
        t(
            "outside-blink",
            "Outside Exterior",
            "Place Blink exterior cameras around home",
            TaskType.DIY,
            trade = Trade.SECURITY,
            effort = Effort.SESSION
        )
    )

    val roomOrder: List<String> = listOf(
        "Kitchen",
        "Long Hallway",
        "Short Hallway",
        "Guest Bath",
        "Laundry Room",
        "Master Bedroom",
        "Master Bathroom — Toilet",
        "Master Bathroom — Main",
        "Main Living Room",
        "Garage",
        "Office",
        "Outside Exterior"
    )
}
