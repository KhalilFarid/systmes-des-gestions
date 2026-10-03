fun main(){
    val u1 = Utilisateur(15, "Farid", "Khalil", "khalil@gmail.com")
    val u2 = Utilisateur(16, "nizar", "nizar", "nizar@gmail.com")
    val l1 = Livre("Boite ", "Ahmed ", "001", 15)
    val l2 = Livre("Antigone", "nizar ", "002", 10)
    val b = Bibliotheque()
    b.ajouterUtilisateur(u1)
    b.ajouterUtilisateur(u2)
    b.ajouterLivre(l1)
    b.ajouterLivre(l2)
    u1.afficherInfos()
    u2.afficherInfos()
    l1.afficherInfos()
    l2.afficherInfos()
    u1.emprunterLivre(l1, "2026/03/20")
    u2.emprunterLivre(l2, "2026/03/21")
    u1.afficherEmprunts()
    u2.afficherEmprunts()
    b.afficherTousLivres()
    val livreT = b.rechercheLivre("Antigone")
    livreT.afficherInfos()
    val e1 = u1.emprunts[0]
    e1.retournerLivre("2026/03/27")
    e1.afficherDetails()
    println("Nombre d'exemplaires a :${l1.nombreExemplaires}")
}
open class Person(var nom:String, var prenom:String, var email:String){
    open fun afficherInfos(){
        println("le nom : $nom , prenom : $prenom , email : $email")
    }
}
class Utilisateur(var idUtilisateur:Int,nom:String, prenom:String,email:String)
    :Person(nom,prenom,email){
    val emprunts: MutableList<Emprunt> = mutableListOf()
    fun emprunterLivre(livre: Livre, dateEmprunt: String) {
        val nouv = Emprunt(this, livre, dateEmprunt)
        emprunts.add(nouv)
    }
    fun afficherEmprunts(){
        if(emprunts.isEmpty()){
            println("emprunts est vide")
        }
        else{
            println(emprunts)
        }
    }
    override fun afficherInfos(){
        println("le nom : $nom , prenom : $prenom , email : $email, idUtilisateur $idUtilisateur")
    }
}
class Livre(var titre:String,var auteur:String,var isbn:String,var nombreExemplaires:Int){
    fun afficherInfos(){
        println("titre : $titre , auteur : $auteur , isbn : $isbn ,nbrExemplqires : $nombreExemplaires")

    }
    fun disponiblePourEmpunt(): Boolean{
        return nombreExemplaires>0

    }
    fun mettreAjourStock(nouveauStock:Int):Int{
        return nombreExemplaires*nouveauStock
    }
}
class Emprunt(
   var utilisateur:Utilisateur,var livre:Livre,var dateEmprunt: String, var dateRetour:String?=null
)
{
    var retourne: Boolean=false
    fun afficherDetails(){
        println("utilisateur : $utilisateur ,livre : $livre,la date emprunt: $dateEmprunt , dateRetour:$dateRetour")
    }
    fun retournerLivre(date:String){
        dateRetour = date
        retourne   = true
        livre.nombreExemplaires++
    }
    }
abstract class GestionBibliotheque {
    val utilisateurs = mutableListOf<Utilisateur>()
    val livres = mutableListOf<Livre>()
    open fun ajouterUtilisateur(utilisateur: Utilisateur){
        utilisateurs.add(utilisateur)}
      open  fun ajouterLivre(livre: Livre){
            livres.add(livre)
        }
   open fun afficherTousLivres(){
        println(livres)
    }
}
class Bibliotheque : GestionBibliotheque() {
    override fun ajouterUtilisateur(utilisateur: Utilisateur) {
        utilisateurs.add(utilisateur)
    }

    override fun ajouterLivre(livre: Livre) {
        livres.add(livre)
    }

    override fun afficherTousLivres() {
            println(livres)
    }
    fun rechercheLivre(titre:String):Livre {
        for (livre in livres) {
            if (livre.titre == titre) {
                return livre
            }
            }
        throw Exception("il n y a pas un livre avec titre $titre")
    }
}
