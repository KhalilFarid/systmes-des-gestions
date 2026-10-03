fun main() {
    val v1 = Voiture(
        "12345-h-1", "Dacia",
        "Logan", 50000,
        4, "Diesel")
    val v2 = Voiture(
        "67890-a-59", "mercedes",
        "class a", 300000,
        4, "Essence"
    )
    val m1 = Moto(
        "11111-C-8",
        "Yamaha", "sss",
        15000, 54
    )
    val m2 = Moto(
        "22222-D-9",
        "bmw", "lsklwss",
        20000, 600
    )
    val c1 = Conducteur("Khalil", "Farid", "1111")
    val c2 = Conducteur("Nizar", "profff", "5555")
    val parc = ParcAutomobile()
    parc.ajouterVehicule(v1)
    parc.ajouterVehicule(v2)
    parc.ajouterVehicule(m1)
    parc.ajouterVehicule(m2)
    v1.afficherDetails()
    v2.afficherDetails()
    m1.afficherDetails()
    m2.afficherDetails()
    c1.afficherDetails()
    c2.afficherDetails()
    try {
        parc.reserverVehicule(
            "12345-h-1", c1,
            "01/10/2026", "05/10/2026")
        println("Reservation faite avec succes")
    } catch (e: Exception) {
        println(e.message)
    }
    try {
        parc.reserverVehicule(
            "67890-a-59", c2,
            "02/10/2026", "06/10/2026")
        println("Reservation faite avec succes")
    } catch (e: Exception) {
        println(e.message)
    }
    try {
        parc.reserverVehicule(
            "12345-h-1", c2,
            "10/10/2026", "15/10/2026")
    } catch (e: VehiculeIndisponibleException) {
        println(e.message)
    }
    try {
        parc.reserverVehicule(
            "99999-X-9", c1,
            "10/10/2026", "15/10/2026")
    } catch (e: VehiculeNonTrouveException) {
        println(e.message)
    }
    parc.afficherVehiculeDispo()
    parc.afficherReservation()
    parc.reservations[0].cloturerReservation(50500)
    parc.afficherVehiculeDispo()
}
abstract class Vehicule(
    val immatriculation: String,
    val marque: String,
    val modele: String,
    var kilometrage: Int,
    var disponible: Boolean = true
){
    open fun afficherDetails(){
        println("le matricule: $immatriculation, marque : $marque ,modele: $modele, kilometrage: $kilometrage,disponible: $disponible")
    }
    fun estDisponible(): Boolean {
        return disponible
    }
    fun marquerIndisponible(){
        disponible=false
    }
    fun marquerDisponible(){
        disponible=true
    }
    fun mettreAJourKilometrage(km:Int){
        kilometrage=km
        println("le nouveau kilometrage $km")
    }
}
class Voiture(
    immatriculation: String,
    marque: String,
    modele: String,
    kilometrage: Int,
    val nombrePortes: Int,
    val typeCarburant: String) : Vehicule(immatriculation, marque, modele, kilometrage){
    override fun afficherDetails() {
  println("le matricule: $immatriculation, marque : $marque ,modele: $modele, kilometrage: $kilometrage,disponible: $disponible ,nombrePortes: $nombrePortes,typeCarburant: $typeCarburant")
    }
}
class Moto(
    immatriculation: String,
    marque: String,
    modele: String,
    kilometrage: Int,
    val cylindre: Int) : Vehicule(immatriculation, marque, modele, kilometrage){
    override fun afficherDetails() {
        println("le matricule: $immatriculation, marque : $marque ,modele: $modele, kilometrage: $kilometrage,disponible: $disponible ,cylindre: $cylindre cm3")
    }
}
class Conducteur(var nom:String,var prenom:String,var numeroPermis: String){
    fun afficherDetails(){
        println("le nom: $nom, prenom: $prenom, numeroPermis: $numeroPermis")
    }
}
class Reservation(val vehicule: Vehicule,val conducteur: Conducteur,
var dateDebut:String,var dateFin:String, var kilometrageDebut:Int,var kilometrageFin: Int?=null)
{
    fun cloturerReservation(kilometrageRetour: Int){
        kilometrageFin = kilometrageRetour
        vehicule.mettreAJourKilometrage(kilometrageRetour)
        vehicule.marquerDisponible()
    }
    fun afficherDetails(){
        println("Véhicule : ${vehicule.immatriculation}")
        println("Conducteur : ${conducteur.nom} ${conducteur.prenom}")
        println("Date début : $dateDebut")
        println("Date fin : $dateFin")
        println("Kilométrage début : $kilometrageDebut")
        println("Kilométrage fin : $kilometrageFin")
    }
}
class ParcAutomobile {
    val vehicules = mutableListOf<Vehicule>()
    val reservations = mutableListOf<Reservation>()
    fun ajouterVehicule(vehicule: Vehicule) {
        vehicules.add(vehicule)
    }
    fun supprimerVehicule(immatriculation: String) {
        vehicules.removeIf {
            it.immatriculation == immatriculation
        }
    }
    fun reserverVehicule(
        immatriculation: String,
        conducteur: Conducteur,
        dateDebut: String,
        dateFin: String
    ) {
        for (v in vehicules) {
            if (v.immatriculation == immatriculation) {
                if (!v.estDisponible()) {
                    throw VehiculeIndisponibleException()
                }
                reservations.add(
                    Reservation(v, conducteur, dateDebut, dateFin, v.kilometrage)
                )
                v.marquerIndisponible()
                return
            }
        }
        throw VehiculeNonTrouveException(immatriculation)
    }
    fun afficherVehiculeDispo(){
        for (v in vehicules){
            if (v.estDisponible()){
                v.afficherDetails()
            }
        }
    }
    fun afficherReservation(){
        for (r in reservations){
            r.afficherDetails()
            }
        }
}
class VehiculeIndisponibleException: Exception( "Le vehicule n est pas disponible")
class VehiculeNonTrouveException(
     immatriculation: String
) : Exception(
    "Le vehicule avec le matricule $immatriculation n existe pas"
)