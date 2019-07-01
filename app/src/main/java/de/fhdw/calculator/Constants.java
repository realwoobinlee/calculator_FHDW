package de.fhdw.calculator;

enum FileNames{
Layout ("layoutformat.json"),

    ;


    FileNames(String s) {
    }
}

enum NatureConstants{
    EGravity (9.81),
    Lightspeed (299792458),
    ElecronCharge (0.00000000000000000016021766),
    Electronmass (0.0000000000000000000000000000009109384),
    Planck (0.0000000000000000000000000000000006626),
    Protonmass (0.00000000000000000000000000167262192369),
    Avogadro (0.0),


    ;


    NatureConstants(double v) {
    }

}